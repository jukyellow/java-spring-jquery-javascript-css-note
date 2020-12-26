module.exports = {
	entry: "./src/script.js",
	output: {
		path: __dirname,
		filename: "./dist/biuld.js",
	},
	module: {
    rules: [
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
}