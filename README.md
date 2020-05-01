&copy;TiramiAsu 「不只要努力的工作，更要有智慧的工作」

<table>
    <td style="border:0px;width:100px;">
        <img style="size:50%;" src="https://avatars0.githubusercontent.com/u/46002098?s=460&amp;v=4"/></td>
    <td style="border:0px;"><h2>onAccountX(記帳系統)</h2><span><b>2020.04.30</b></span></td>
</table>



## [ 資料庫架構 ]

![](https://github.com/TiramiAsu/onAccountX/blob/master/src/main/webapp/vue-x/src/assets/tables.png)



| **Table**                  | **Description**                                              |
| -------------------------- | ------------------------------------------------------------ |
| **1-1**   **會員資料**     | 建立基本會員資料，每個會員可建立多的帳號                     |
| **1-2**   **帳戶資料**     | 可依照帳戶紀錄不同的日記帳務(2-2)及現金帳務資料(2-3)         |
| **2-1**   **帳務科目資料** | 自定義會計科目，便可於日記帳務(2-2)及現金帳務資料(2-3)編輯時選用，便於紀錄 |
| **2-2**   **日記帳務資料** | 所有開銷皆需紀錄，包含銀行帳戶匯款等不使用現金之紀錄         |
| **2-3**   **現金帳務資料** | 所有使用到現金的帳務，皆需另外紀錄在現金帳務(新增日記帳務時，紀錄中有現金科目系統會自動記錄) |
| **View**                   | 使用 SQL 將分別將借項(Debit)與貸項(Credit) 加總在一起，便於財務報表中統計 |





## [ 系統架構 ]

![](https://github.com/TiramiAsu/onAccountX/blob/master/src/main/webapp/vue-x/src/assets/system.png)



| **Item**      | **Description**                                              |
| ------------- | ------------------------------------------------------------ |
| **Front-end** | 使用 Vue.js 結合 Bootstrap   與 Google Charts 呈現資料       |
| **Back-end**  | 使用 Java 利用 Spring   Framework 開發系統，配合 Maven 管理 libaray，架構出 API 使 Vue.js 可以透過RESTful   的設計方式存取資料。<br />**RESTService** ->利用許多 Service將資料處理後包裝成 Response 回傳<br/>**Service**  ->  利用許多 DAO 將 Request 中的 json 解析後處理，回傳物件給 RESTService<br/>**DAO** -> 使用 Google DAO 及 Spring Framework 的 hibernate 存取 Entity，並建立基本對 Entity 存取之處理(CRUD) |
| **Database**  | 使用 PostgreSQL 作為資料庫存取資料                           |
| **Git**       | 使用 GitHub 管理開發系統版本                                 |



## [ 後台 UI ]

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/back-endUI-II.png) 

| **Item**             | **Description**                     |
| -------------------- | ----------------------------------- |
| **Project**          | 資料庫架構、系統架構                |
| **Report**           | 財務報表                            |
| **Journal   / Cash** | 日記帳務資料維護 / 現金帳務資料維護 |
| **Subject**          | 會計科目維護                        |
| **Accounts**         | 帳戶資料維護                        |
| **Members**          | 會員資料維護                        |
| **My   Profile**     | 本人履歷                            |



## [ 維護 UI ]

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/維護UI-I.png) 

各維護頁面共同功能如下：

| **Item**   | **Description**                                              |
| ---------- | ------------------------------------------------------------ |
| **Filter** | 使用 Google DAO 可查詢資料、查日期等(含文字的模糊查詢)       |
| **Add**    | 新增此資料表資料，UI 會做切換                                |
| **Edit**   | 針對單筆資料做編輯，UI 會做切換(在 UI 中會檢查資料並確認更新) |
| **Delete** | 針對單筆資料做刪除，會有對話框做確認                         |



| 頁面切換                                                     |
| :----------------------------------------------------------- |
| “新增”                                                       |
| ![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/維護UI-II.png) |
| “編輯”                                                       |
| ![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/維護UI-III.png) |



## [ 財務報表 ]

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/財務報表-I.png)

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/財務報表-IIx.png)



## [ 履歷 ]

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/profiles-1.png)

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/profiles-2.png)

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/profiles-3.png)

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/profiles-4.png)

![](https://github.com/TiramiAsu/onAccountX/blob/master/imgs/profiles-5.png)