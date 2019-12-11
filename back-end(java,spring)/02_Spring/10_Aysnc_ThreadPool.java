
@Configuration
@EnableAsync
@RefreshScope
public class SpringAsyncConfig {
	/* pool 사이즈가 db사이즈보다 크지 않게 설정해야함,
	* pool이 가득찬경우 로그만 저장하지 않고 빠르게 종료하는게 gateway 성능지연을 막을 수 있음
	* */
	@Value("${spring.db1.datasource.maxActive}") //100
	private int MAX_CONN;
	private int CORE_POOL_SIZE;
	private int MAX_POOL_SIZE;
	private int QUE_CAPACITY = MAX_POOL_SIZE;
	
	@PostConstruct
	public void setSizeInfo() {
		CORE_POOL_SIZE = (int) (MAX_CONN/2); //50
		MAX_POOL_SIZE = (int) (MAX_CONN * 0.9); //90, zuul에서 기본으로 사용할 conn 남겨둠
		QUE_CAPACITY = MAX_CONN;
	}

	protected Logger logger = LoggerFactory.getLogger(SpringAsyncConfig.class);

	@Bean(name = "logThreadPoolTaskExecutor", destroyMethod = "destroy")
	public Executor threadPoolTaskExecutor() {
		logger.info("[logThreadPoolTaskExecutor] "
		+ "CORE_POOL_SIZE:"+CORE_POOL_SIZE+",MAX_POOL_SIZE:"+MAX_POOL_SIZE+",QUE_CAPACITY:"+QUE_CAPACITY);
		
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
		taskExecutor.setQueueCapacity(QUE_CAPACITY);
		taskExecutor.setThreadNamePrefix("Executor-");
		taskExecutor.initialize();

		return new HandlingExecutor(taskExecutor); // HandlingExecutor로 wrapping 합니다.
	}


	public class HandlingExecutor implements AsyncTaskExecutor {
	private AsyncTaskExecutor executor;

	public HandlingExecutor(AsyncTaskExecutor executor) {
		this.executor = executor;
	}

	@Override
	public void execute(Runnable task) {
		executor.execute(createWrappedRunnable(task));
	}

	@Override
	public void execute(Runnable task, long startTimeout) {
		executor.execute(createWrappedRunnable(task), startTimeout);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return executor.submit(createWrappedRunnable(task));
	}

	@Override
	public <T> Future<T> submit(final Callable<T> task) {
		return executor.submit(createCallable(task));
	}

	private <T> Callable<T> createCallable(final Callable<T> task) {
		return new Callable<T>() {
			@Override
			public T call() throws Exception {
			try {
				return task.call();
			} catch (Exception ex) {
				ex.printStackTrace();
				handle(ex);
				throw ex;
			}
		}
	};
}

	private Runnable createWrappedRunnable(final Runnable task) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					task.run();
				} catch (Exception ex) {
					ex.printStackTrace();
					handle(ex);
				}
			}
		};
	}

	private void handle(Exception ex) {
		//errorLogger.info("Failed to execute task. : {}", ex.getMessage());
		logger.error("[HandlingExecutor] " + ex.getMessage());
	}

	public void destroy() {
		if(executor instanceof ThreadPoolTaskExecutor){
			((ThreadPoolTaskExecutor) executor).shutdown();
			}
		}
	}
}

//Async 메소드 사용시
@Async("logThreadPoolTaskExecutor")
public void saveLog2DB(LogMsg logMsg, String EVT_TYPE, String requestURI, String currDateTime) {
	int insertCnt = 0;
	try {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UUID", logMsg.uuid);
		params.put("EVT_TYPE", EVT_TYPE);
		params.put("API_ID", logMsg.apiId);
		params.put("API_URI", requestURI);
		if(LogMsg.DB_LOG_EVT_ROUTE.equals(EVT_TYPE)) {
			params.put("HOST_IP", "");
			params.put("HOST_PORT", "");
			params.put("CONT_NAME", "");
		}else {
			params.put("HOST_IP", logMsg.hostIp);
			params.put("HOST_PORT", serverPort);
			params.put("CONT_NAME", logMsg.contName);
		}
		params.put("USER_ID", logMsg.userId);
		params.put("USER_IP", logMsg.userIp);
		params.put("EVT_DATE", currDateTime);
		
		insertCnt = msaMapper.insertLogData(params);
	}catch(Exception e) {
		e.printStackTrace();
		throw e;
	}
}