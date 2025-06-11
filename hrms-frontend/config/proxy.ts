export default {
  dev: {
    '/api/': {
      // 要代理的地址
      target: 'http://localhost:8080',
      // 配置这个 可以从 http 代理到 https
      // 依赖 origin 的功能可能需要这个 比如 cookie
      changeOrigin: true,
    },
  },
  test: {
    '/api/': {
      target: 'https://proapi.azurewebsites.net',
      changeOrigin: true,
      pathRewrite: {'^': ''},
    },
  },
  pre: {
    '/api/': {
      target: 'your pre url',
      changeOrigin: true,
      pathRewrite: {'^': ''},
    },
  },
};
