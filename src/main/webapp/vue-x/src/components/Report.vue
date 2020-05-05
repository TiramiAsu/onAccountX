<template>
  <div style="font-size: 10 px">
    <div style="padding-top: 1%">
      <div>
        <h1>Report</h1>
        <h6 style="color: lightgray">
          <span>{{ PARAMS.Layout.Manage.text }}</span>
        </h6>
      </div>
      <br />
      <div>
        <!-- Main UI -->
        <div>
          <span>
            <loading :display="display" :code="loadingCode" />
          </span>
          <div v-if="display === false">
            <!--
            <select class="custom-select"
              v-model="filterCondition.year" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
              <option v-for="(option, index) in filterCondition.years" :key="index" :value="option.value">{{ option.text }}</option>
            </select>
            -->
            <div class="container">
              <div class="row">
                <!-- Left -->
                <div class="col-sm">
                  <div align="center">{{ PARAMS.PieChartTitle.income.name }}</div>
                  <div align="center"><h3><b>{{ showMoneyFormat(PARAMS.PieChartTitle.income.total) }}</b></h3></div>
                  <GChart style="width: 500px; height: 400px"
                    type="PieChart"
                    :data="chartDataLeft"
                    :options="chartOptions"
                  />
                </div>
                <!-- Right -->
                <div class="col-sm">
                  <div align="center">{{ PARAMS.PieChartTitle.expense.name }}</div>
                  <div align="center"><h3><b>{{ showMoneyFormat(PARAMS.PieChartTitle.expense.total) }}</b></h3></div>
                  <GChart style="width: 500px; height: 400px"
                    type="PieChart"
                    :data="chartDataRight"
                    :options="chartOptions"
                  />
                </div>
              </div>
              <!--
              <div align="center"><h4>{{ PARAMS.TableChart.title.asset }}</h4></div>
              <div style="padding-left: 10%">
                <GChart
                  :settings="{ packages: ['table'] }"
                  type="Table"
                  :data="chartDataAssetLiabilities"
                  :options="chartOptionsTable"
                  :events="chartEventsTable"
                  ref="gChart"
                />
              </div>
              -->
              <p />
              <div align="center"><h4>{{ PARAMS.TableChart.title.income }}</h4></div>
              <div style="padding-left: 10%">
                <GChart
                  :settings="{ packages: ['table'] }"
                  type="Table"
                  :data="chartDataIncomeExpense"
                  :options="chartOptionsTable"
                  :events="chartEventsTable"
                  ref="gChart"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import loading from './Loading.vue'
import { GChart } from 'vue-google-charts'

export default {
  name: 'Report',
  components: {
    'loading': loading,
    GChart
  },
  data () {
    return {
      API: {
        entityName: 'report',
        subjectCodeName: {
          method: 'get',
          url: '/onAccountX/srv/subject/list/name'
        },
        reportGroupByDebit: {
          method: 'get',
          url: '/onAccountX/srv/report/journal/groupby/debit'
        },
        reportGroupByCredit: {
          method: 'get',
          url: '/onAccountX/srv/report/journal/groupby/credit'
        },
        reportTable: {
          method: 'post',
          url: '/onAccountX/srv/report/table'
        }
      },
      PARAMS: {
        Layout: {
          Manage: { symbol: 'manage', value: 0, text: '財務報表' }
        },
        PieChartTitle: {
          income: { name: '年收入', code: '2-1', total: 0 },
          expense: { name: '年支出', code: '2-2', total: 0 }
        },
        TableChart: {
          title: {
            income: '收入 & 支出',
            asset: '資產 & 負債'
          },
          income: { name: '收入', code: '2-1', total: 0 },
          expense: { name: '支出', code: '2-2', total: 0 },
          asset: { name: '資產', code: '1-1', total: 0 },
          liabilities: { name: '負債', code: '1-2', total: 0 }
        }
      },
      report: {
        timeDate: null,
        timeDateEnd: null
      },
      entityList: [], // all data
      entityListGroupByDebit: [], // all data (group by Debit)
      entityListGroupByCredit: [], // all data (group by Credit)
      subjectList: [], // all subject info
      journalList: [],
      entityListTable: [], // all 1-12 month data
      filterCondition: {
        year: '2019',
        years: []
      },

      // loading
      display: true,
      loadingCode: 0,

      /* Google Charts */

      // pie chart
      chartData: [],
      chartDataLeft: [], // left PieChart data
      chartDataRight: [], // right PieChart data
      chartTitlePie: ['subject', 'subtotal'],
      // chartData: [
      //   ['Task', 'Hours per Day'],
      //   ['Work', 20],
      //   ['Eat', 2],
      //   ['Commute', 2],
      //   ['Watch TV', 2],
      //   ['Sleep', 7]
      // ],
      chartOptions: {
        chart: {
          title: 'My Daily Activities',
          subtitle: 'test'
        }
      },

      // table
      chartDataAssetLiabilities: [],
      chartDataIncomeExpense: [],
      chartTitleTable: [
        { type: 'string', label: 'code', id: 'code' },
        { type: 'string', label: 'subject', id: 'subject' },
        { type: 'number', label: '  1 月 ', id: 'm1' },
        { type: 'number', label: '  2 月 ', id: 'm2' },
        { type: 'number', label: '  3 月 ', id: 'm3' },
        { type: 'number', label: '  4 月 ', id: 'm4' },
        { type: 'number', label: '  5 月 ', id: 'm5' },
        { type: 'number', label: '  6 月 ', id: 'm6' },
        { type: 'number', label: '  7 月 ', id: 'm7' },
        { type: 'number', label: '  8 月 ', id: 'm8' },
        { type: 'number', label: '  9 月 ', id: 'm9' },
        { type: 'number', label: ' 10 月 ', id: 'm10' },
        { type: 'number', label: ' 11 月 ', id: 'm11' },
        { type: 'number', label: ' 12 月 ', id: 'm12' }
      ],
      // chartData: [
      //   [
      //     { type: 'string', label: 'President', id: 'President' },
      //     { type: 'date', label: 'From', id: 'From' },
      //     { type: 'date', label: 'To', id: 'To' }
      //   ],
      //   ['Washington', new Date(1789, 3, 30), new Date(1797, 3, 4)],
      //   ['Adams', new Date(1797, 2, 4), new Date(1802, 2, 4)],
      //   ['Jefferson', new Date(1801, 2, 4), new Date(1809, 2, 4)]
      // ],
      chartOptionsTable: {
        chart: {
          title: 'Company Performance',
          subtitle: 'Sales, Expenses, and Profit: 2014-2017'
        }
      },
      chartEventsTable: {
        // select: () => {
        //   const table = this.$refs.gChart.chartObject
        //   const selection = table.getSelection()
        //   const onSelectionMeaasge = selection.length !== 0 ? 'row was selected' : 'row was diselected'
        //   alert(onSelectionMeaasge)
        // }
      }
    }
  },
  mounted () {
    this.queryReportGroupBy()
    this.querySubjectCodeName()
    this.queryReportTable()
  },
  methods: {
    /* API */

    querySubjectCodeName () {
      var self = this
      axios({
        method: self.API.subjectCodeName.method,
        url: self.API.subjectCodeName.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        }
      }).then(function (response) {
        if (response) {
          self.subjectList = response.data.data
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query Subject Names failed: ', error)
      })
    },
    queryReportTable () {
      var self = this
      // var preChartData = []
      axios({
        method: self.API.reportTable.method,
        url: self.API.reportTable.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: { year: self.filterCondition.year }
      }).then(function (response) {
        if (response) {
          self.entityListTable = response.data.data
          self.tableReport(response.data.data)
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query ' + self.API.entityName + ' failed: ', error)
      })
    },
    queryReportGroupBy () {
      var self = this
      var preChartDataLeft = []
      var preChartDataRight = []
      // Left Charts
      axios({
        method: self.API.reportGroupByCredit.method,
        url: self.API.reportGroupByCredit.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        }
      }).then(function (response) {
        if (response) {
          self.entityListGroupByCredit = response.data.data
          // 處理成 charts 用 data
          preChartDataLeft.push(self.chartTitlePie)
          response.data.data.forEach(report => {
            var arr = []
            arr.push(report.credit)
            arr.push(report.subtotal)
            preChartDataLeft.push(arr)
          })
          self.chartDataLeft = preChartDataLeft
          self.getLeftData(self)
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query  GroupBy Credit failed: ', error)
      })
      // Right Charts
      axios({
        method: self.API.reportGroupByDebit.method,
        url: self.API.reportGroupByDebit.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        }
      }).then(function (response) {
        if (response) {
          self.entityListGroupByDebit = response.data.data
          // 處理成 charts 用 data
          preChartDataRight.push(self.chartTitlePie)
          response.data.data.forEach(report => {
            var arr = []
            arr.push(report.debit)
            arr.push(report.subtotal)
            preChartDataRight.push(arr)
          })
          self.chartDataRight = preChartDataRight
          self.getRightData(self)
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query GroupBy Debit failed: ', error)
      })
    },

    /* API - other */

    tableReport (list) {
      var self = this
      var codeName = list.codeName
      var tableDataDebit = list.tableDataDebit
      var tableDataCredit = list.tableDataCredit

      var debitArr = {
        all: [],
        asset: [],
        expense: [],
        other: []
      }
      var creditArr = {
        all: [],
        income: [],
        liabilities: [],
        other: []
      }

      // 先給 subject & 1~12 month subtotal
      debitArr.all = self.getEmptydataArray(codeName, Object.keys(tableDataDebit).length)
      creditArr.all = self.getEmptydataArray(codeName, Object.keys(tableDataCredit).length)

      // arr 再塞資料
      debitArr.all = self.getGroupByData(tableDataDebit, debitArr.all)
      creditArr.all = self.getGroupByData(tableDataCredit, creditArr.all)

      // classification
      var obj1 = self.getClassification('debit', debitArr.all, self.PARAMS.TableChart.asset, self.PARAMS.TableChart.expense)
      var obj2 = self.getClassification('credit', creditArr.all, self.PARAMS.TableChart.income, self.PARAMS.TableChart.liabilities)

      // 支出, 資產 -> debit
      debitArr.asset = obj1.asset
      debitArr.expense = obj1.expense
      debitArr.other = obj1.other

      // 收入, 負債 -> credit
      creditArr.income = obj2.income
      creditArr.liabilities = obj2.liabilities
      creditArr.other = obj2.other

      // sort
      // arr = self.sortBySubjectCode(arr)
      debitArr.asset = self.beautifyData(self.sortBySubjectCode(debitArr.asset))
      debitArr.expense = self.beautifyData(self.sortBySubjectCode(debitArr.expense))
      debitArr.other = self.beautifyData(self.sortBySubjectCode(debitArr.other))

      creditArr.income = self.beautifyData(self.sortBySubjectCode(creditArr.income))
      creditArr.liabilities = self.beautifyData(self.sortBySubjectCode(creditArr.liabilities))
      creditArr.other = self.beautifyData(self.sortBySubjectCode(creditArr.other))

      // set title
      // asset & liabilities
      var al = debitArr.asset.concat(creditArr.liabilities)
      al.splice(0, 0, self.chartTitleTable)
      self.chartDataAssetLiabilities = al

      // income & excepense
      var ie = []
      var sumArrIncome = self.getSumArray('sum', '總收入(A)', creditArr.income)
      var sumArrExpense = self.getSumArray('sum', '總支出(B)', debitArr.expense)

      var finalArr = []
      finalArr.push(sumArrIncome)
      finalArr.push(sumArrExpense)
      var surplusOrDeficitArr = self.getSumArray('minus', '收支餘絀(C = A - B)', finalArr)

      creditArr.income.push(sumArrIncome)
      debitArr.expense.push(sumArrExpense)
      debitArr.expense.push(surplusOrDeficitArr)

      ie = creditArr.income.concat(debitArr.expense)
      ie.splice(0, 0, self.chartTitleTable)
      self.chartDataIncomeExpense = ie
    },
    getClassification (symbol, arr, bigSubject1, bigSubject2) {
      var a1 = []
      var a2 = []
      var other = []
      for (var row in arr) {
        var subjectCode = arr[row][0][0]
        if (subjectCode.substring(0, 3) === bigSubject1.code) {
          // debit -> asset;   credit -> income
          a1.push(arr[row])
        } else if (subjectCode.substring(0, 3) === bigSubject2.code) {
          // debit -> expense; credit -> liabilities
          a2.push(arr[row])
        } else {
          other.push(arr[row])
        }
      }
      var obj = {}
      switch (symbol) {
        case 'debit':
          obj = { 'asset': a1, 'expense': a2, 'other': other }
          break
        case 'credit':
          obj = { 'income': a1, 'liabilities': a2, 'other': other }
          break
      }
      // console.log(obj)
      return obj
    },
    getSumArray (symbol, title, arr) {
      var returnArr = [null, title]
      var rowLength = arr[0].length
      switch (symbol) {
        case 'sum':
          for (var i = 2; i < rowLength; i++) {
            var sumIncome = 0
            for (var row in arr) {
              sumIncome += arr[row][i]
            }
            returnArr.push(sumIncome)
          }
          break
        case 'minus':
          var arrIncome = arr[0]
          var arrExpense = arr[1]
          for (i = 2; i < rowLength; i++) {
            returnArr.push(arrIncome[i] - arrExpense[i])
          }
      }
      return returnArr
    },
    getEmptydataArray (codeName, countEmpty) {
      var arr = []
      for (var subj in codeName) {
        var name = codeName[subj]
        var temp = [[subj, name]]
        for (var i = 1; i < (countEmpty + 1); i++) {
          temp.push(0) // subtotal
        }
        arr.push(temp)
      }
      return arr
    },
    getGroupByData (tableData, emptyDataArray) {
      for (var month in tableData) { // 取得 month 資料
        var datas = tableData[month]
        for (var subj in datas) { // 取得 obj
          var subtotal = datas[subj]
          // console.log(subj + ":" + subtotal) // 1-1-1-1:現金
          emptyDataArray.forEach(row => {
            if (row[0][0] === subj) { // row = [subject, subtotal]
              row[Number(month)] = subtotal
            }
          })
        }
      }
      return emptyDataArray
    },
    // 取得 left Chart data
    getLeftData (self) {
      var code = self.PARAMS.PieChartTitle.income.code // 2-1
      var preChartData = []
      var total = 0
      self.entityListGroupByCredit.forEach(data => {
        if (data.code.substring(0, 3) === code) {
          var arr = []
          arr.push(data.credit)
          arr.push(data.subtotal)
          preChartData.push(arr)
          total += data.subtotal
        }
      })
      preChartData.sort(function (a, b) {
        return a[1] < b[1] ? 1 : -1
      })
      preChartData.splice(0, 0, self.chartTitlePie)
      self.chartDataLeft = preChartData
      self.PARAMS.PieChartTitle.income.total = total
    },
    // 取得 right Chart data
    getRightData (self) {
      var code = self.PARAMS.PieChartTitle.expense.code // 2-2
      var preChartData = []
      var total = 0
      self.entityListGroupByDebit.forEach(data => {
        if (data.code.substring(0, 3) === code) {
          var arr = []
          arr.push(data.debit)
          arr.push(data.subtotal)
          preChartData.push(arr)
          total += data.subtotal
        }
      })
      self.chartDataRight = preChartData.sort(function (a, b) {
        return a[1] < b[1] ? 1 : -1
      })
      preChartData.splice(0, 0, self.chartTitlePie)
      self.chartDataRight = preChartData
      self.PARAMS.PieChartTitle.expense.total = total
    },
    beautifyData (arr) {
      var finalArr = []
      for (var row in arr) {
        var newArr = []
        var tab = '__'
        newArr.push(arr[row][0][0]) // 1-0-9-1
        switch (arr[row][0][0].length) {
          case 7:
            // "      薩莉亞薪資" (code: "1-1-1-1")
            newArr.push(tab + tab + tab + arr[row][0][1])
            break
          case 5:
            // "    薪資收入" (code: "1-1-1")
            newArr.push(tab + tab + arr[row][0][1])
            break
          case 3:
            // "  收入" (code: "1-1")
            newArr.push(tab + arr[row][0][1])
            break
          case 1:
            // "收支餘絀表" (code: "1")
            newArr.push(arr[row][0][1]) // code
            break
        }
        for (var i = 0; i < 12; i++) {
          var num = arr[row][1 + i] // 從 index 1 開始
          newArr.push((num === 0 ? null : num))
        }
        finalArr.push(newArr)
      }
      // console.log(finalArr)
      return finalArr
    },

    // 檢查時間先後
    checkTime (d1, d2) {
      // console.log('typeof    bean.timeDate: ', typeof bean.timeDate)    // string
      // console.log('typeof bean.timeDateEnd: ', typeof bean.timeDateEnd) // string
      // 如果只選一個日期, 則只查當天
      var tsTimeDate = -1
      var tsTimeDateEnd = -1
      var aDay = 24 * 60 * 60 * 1000
      if (d1) {
        tsTimeDate = new Date(d1).getTime()
      }
      if (d2) {
        tsTimeDateEnd = new Date(d2).getTime()
      }
      var vStart = tsTimeDate !== -1
      var vEnded = tsTimeDateEnd !== -1
      var start = null
      var ended = null
      if (vStart && vEnded) {
        if (tsTimeDate === tsTimeDateEnd) {
          start = tsTimeDate - aDay
          ended = tsTimeDateEnd
        } else if (tsTimeDate < tsTimeDateEnd) {
          start = tsTimeDate - aDay
          ended = tsTimeDateEnd
        } else {
          start = tsTimeDateEnd
          ended = tsTimeDate - aDay
        }
      } else if (vStart || vEnded) {
        if (vStart) {
          start = tsTimeDate - aDay
          ended = null
        }
        if (vEnded) {
          start = null
          ended = tsTimeDateEnd
        }
      }
      return { 'start': start, 'ended': ended }
    },
    // 比較 subject 順序
    sortBySubjectCode (arr) {
      /*
      arr = [
        [["2-2-1-2", "C"], 3, 7],
        [["1-1-1-1", "A"], 1, 5],
        [["2-2-1-1", "B"], 2, 6]
      ]
      */
      var self = this
      var ok = 0 // 確定數量
      while (ok < arr.length) {
        for (var i = 0; i < arr.length - ok; i++) {
          var i2 = i + 1
          if (i2 === arr.length) {
            break
          }
          var num1 = self.subjectToNumber(arr[i][0][0])
          var num2 = self.subjectToNumber(arr[i2][0][0])
          if (num1 > num2) {
            var temp = arr[i]
            arr[i] = arr[i2]
            arr[i2] = temp
          }
        }
        ok++
      }
      return arr
      /*
      arr = [
        ["1-1-1-1", 1, 5],
        ["2-2-1-1", 2, 6],
        ["2-2-1-2", 3, 7]
      ]
      */
    },
    // subject 轉 number (必須為個位數)
    subjectToNumber (code) {
      // code = "1-2-2-1"
      var arr = code.split('-')
      // arr length < 4, 則補到 4
      switch (arr.length) {
        case 1:
          arr.push('0')
          arr.push('0')
          arr.push('0')
          break
        case 2:
          arr.push('0')
          arr.push('0')
          break
        case 3:
          arr.push('0')
      }
      var sum = 0
      for (var i = 0; i < arr.length; i++) {
        sum += Number(arr[i]) * Math.pow(10, arr.length - (i + 1))
      }
      return sum // 1221
    },
    showMoneyFormat (num) {
      var str = ''
      if (typeof num === 'number') {
        str = '$ ' + num.toLocaleString('en-US')
      } else {
        str = 'Error'
      }
      return str
    },

    /* Util */

    // [時間格式] 取得格式化(YYY-MM-DD HH:mm)後的日期時間字串
    toFormatDateTime (timestamp, formatStr) {
      /* 1: timestamp 傳入是日期(string)   '2019-7-12 9:17', 則回傳 '2019-07-12 09:17'
       * 2: timestamp 傳入是時間戳(number) 1562894220000, 則回傳 '2019-07-12 09:17'
       * 3: timestamp 傳入是空的(null)     (null), 則回傳 'null'
       * p.s. formatStr 為 Y(年) M(月) D(日) H(時) m(分) s(秒) 組成 */
      // 變成 Date 物件，再拿時間戳(getTime() 回傳的是 number 型態)
      var fmtStr = (formatStr === undefined || formatStr === null) ? 'YYYY-MM-DD HH:mm' : formatStr
      if (timestamp) {
        switch (typeof timestamp) {
          case 'number':
            return moment(timestamp).format(fmtStr)
          case 'string':
            timestamp = new Date(timestamp).getTime()
            return moment(timestamp).format(fmtStr)
          default:
            console.log('Error: 時間轉換型態必須是 Number 或 String')
        }
      }
      return null
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2, h6, span {
  font-weight: normal;
  text-align: center;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.btn-circle {
  width: 30px;
  height: 30px;
  padding: 6px 0px;
  border-radius: 15px;
  text-align: center;
  font-size: 12px;
  line-height: 1.42857;
}
</style>
