package com.test.enumtype;

public class EnumTestMain {
	private static enum  PERSON_TYPE {
		MAN("남자"), WOMAN("여자"), CHILDREN("아이");
		
		private String desc;
		private PERSON_TYPE(String desc) {
			System.out.println("PERSON_TYPE called: " + desc);
			this.desc = desc;
		}
		public String getTypeDesc() {
			return this.desc;
		}
	}
	
	public static void main( String[] args ) {
		for(PERSON_TYPE type : PERSON_TYPE.values()) {
			System.out.println("type: " + type + ",desc:" + type.getTypeDesc());
		}
		
		PERSON_TYPE a = PERSON_TYPE.MAN;
		PERSON_TYPE b = PERSON_TYPE.WOMAN;
		if(a==b) {
			System.out.println("a==b");
		}else {
			System.out.println("a!=b");
		}		
	}
	
	
}
