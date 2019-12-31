/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx;

/**
 * <pre>
 * [About] 2019-12-26 15:36
 * 
 * [Vue.js]
 * vue-x/config/index.js
 * - build 為 boundle 路徑, 設定輸出到特定位置
 * - index: 為打包後執行 js 的 html
 * - assetsRoot: 為輸出的指定資料夾
 * - assetsSubDirectory: 為輸出 js 的指定資料夾
 * - assetsPublicPath: WebContent 的存取位置
 * 
 * vue-x/build/webpack.dev.conf.js
 * - devServer: 為啟動 Server 時的根目錄, 專案名稱設定於 from // 中
 * 
 * [Maven]
 * onAccountX/WebContent/WEB-INF/web.xml
 * - <error-page />: 設定當 Vue 經由 router 到某 URL 時重新整理, 出現 404 要再轉導到 Vue 根目錄, 重新透過路由找到頁面
 * 
 * [JAX-RS] Jersey
 * onAccountX/pom.xml
 * 1. 資源載入:
 * - org.glassfish.jersey.core | jersey-server | 2.23.2
 * - org.glassfish.jersey.core | jersey-common | 2.23.2
 * - org.glassfish.jersey.containers | jersey-container-servlet | 2.23.2
 * 2. 支援 Json 格式: org.glassfish.jersey.media | jersey-media-json-jackson | 2.23.2
 * 
 * onAccountX/com/onaccountx/restful/_Application.java
 * - 設定 RESTful 應用根目錄, API 即可使用 Annotation 分配資源
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface _onAccountX {
	/**
	 * TODO
	 * [Java]
	 * - OOP: design rule -> https://skyyen999.gitbooks.io/-study-design-pattern-in-java/content/oodPrinciple.html
	 * - database: Sequence -> https://blog.csdn.net/zqg4919/article/details/74669886
	 * 
	 * [vue]
	 * - route 刷新問題: https://www.itread01.com/content/1544493607.html
	 */
}
