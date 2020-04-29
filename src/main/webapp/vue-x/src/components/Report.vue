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
                  <div align="center">{{ reportTitle.left.name }}</div>
                  <div align="center"><h3><b>{{ showMoneyFormat(reportTitle.left.total) }}</b></h3></div>
                  <GChart style="width: 500px; height: 400px"
                    type="PieChart"
                    :data="chartDataLeft"
                    :options="chartOptions"
                  />
                </div>
                <!-- Right -->
                <div class="col-sm">
                  <div align="center">{{ reportTitle.right.name }}</div>
                  <div align="center"><h3><b>{{ showMoneyFormat(reportTitle.right.total) }}</b></h3></div>
                  <GChart style="width: 500px; height: 400px"
                    type="PieChart"
                    :data="chartDataRight"
                    :options="chartOptions"
                  />
                </div>
              </div>
              <div style="padding-left: 10%">
                <GChart
                  :settings="{ packages: ['table'] }"
                  type="Table"
                  :data="chartDataTable"
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
        subjectNames: {
          method: 'get',
          url: '/onAccountX/srv/subject/list/name'
        },
        reportJournal: {
          method: 'get',
          url: '/onAccountX/srv/report/journal'
        },
        reportJournalGroupBy: {
          method: 'get',
          url: '/onAccountX/srv/report/journal/groupby'
        },
        reportTable: {
          method: 'post',
          url: '/onAccountX/srv/report/table'
        }
      },
      PARAMS: {
        Layout: {
          Manage: { symbol: 'manage', value: 0, text: '財務報表' }
        }
      },
      reportTitle: {
        left: { name: '年收入', code: '2-1', total: 0 },
        right: { name: '年支出', code: '2-2', total: 0 }
      },
      report: {
        timeDate: null,
        timeDateEnd: null
      },
      entityList: [], // all data
      entityListGroupBy: [], // all data (group by subject name)
      subjectList: [], // all subject info
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
      chartTitlePie: ['Task', 'Hours per Day'],
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
      chartDataTable: [],
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
        select: () => {
          const table = this.$refs.gChart.chartObject
          const selection = table.getSelection()
          const onSelectionMeaasge = selection.length !== 0 ? 'row was selected' : 'row was diselected'
          alert(onSelectionMeaasge)
        }
      }
    }
  },
  mounted () {
    this.queryReportJournal()
    this.queryReportJournalGroupBy()
    this.querySubjectNames()
    this.queryReportTable()
  },
  methods: {
    /* API */

    querySubjectNames () {
      var self = this
      axios({
        method: self.API.subjectNames.method,
        url: self.API.subjectNames.url,
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
    queryReportJournal () {
      var self = this
      // var preChartData = []
      axios({
        method: self.API.reportJournal.method,
        url: self.API.reportJournal.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        }
      }).then(function (response) {
        if (response) {
          self.entityList = response.data.data
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query ' + self.API.entityName + ' failed: ', error)
      })
    },
    queryReportJournalGroupBy () {
      var self = this
      var preChartData = []
      axios({
        method: self.API.reportJournalGroupBy.method,
        url: self.API.reportJournalGroupBy.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        }
      }).then(function (response) {
        if (response) {
          self.entityListGroupBy = response.data.data
          // 處理成 charts 用 data
          preChartData.push(self.chartTitlePie)
          response.data.data.forEach(report => {
            var arr = []
            arr.push(report.debit)
            arr.push(report.subtotal)
            preChartData.push(arr)
          })
          self.chartData = preChartData
          self.getLeftData(self)
          self.getRightData(self)
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query ' + self.API.entityName + ' failed: ', error)
      })
    },

    /* API - other */

    tableReport (list) {
      var self = this
      var codeName = list.codeName
      var tableData = list.tableData
      var arr = [] // all subject
      var arrIncome = [] // code 2-1-x-x
      var arrExpense = [] // code 2-2-x-x
      var arrOther = []
      var count = 0
      for (var month in tableData) {
        count++
      }
      // arr 先給 subject & 1~12 month subtotal
      for (var subj in codeName) {
        var name = codeName[subj]
        var temp = [[subj, name]]
        for (var i = 1; i < (count + 1); i++) {
          temp.push(0) // subtotal
        }
        arr.push(temp)
      }
      // arr 再塞資料
      for (month in tableData) { // 取得 month 資料
        var datas = tableData[month]
        for (subj in datas) { // 取得 obj
          var subtotal = datas[subj]
          // console.log(subj + ":" + subtotal) // 1-0-9-1:現金
          arr.forEach(row => {
            if (row[0][0] === subj) { // row = [subject, subtotal]
              row[Number(month)] = subtotal
            }
          })
        }
      }
      // classification
      for (var row in arr) {
        var subjectCode = arr[row][0][0]
        if (subjectCode.substring(0, 3) === self.reportTitle.left.code) {
          arrIncome.push(arr[row])
        } else if (subjectCode.substring(0, 3) === self.reportTitle.right.code) {
          arrExpense.push(arr[row])
        } else {
          arrOther.push(arr[row])
        }
      }
      // sort
      // arr = self.sortBySubjectCode(arr)
      arrIncome = self.sortBySubjectCode(arrIncome)
      arrExpense = self.sortBySubjectCode(arrExpense)
      arrOther = self.sortBySubjectCode(arrOther)

      arrIncome = self.beautifyData(arrIncome)
      arrExpense = self.beautifyData(arrExpense)
      arrOther = self.beautifyData(arrOther)

      // set title
      var newArr = arrIncome.concat(arrExpense) // .concat(arrOther)
      newArr.splice(0, 0, self.chartTitleTable)
      // arr.splice(0, 0, self.chartTitleTable)

      self.chartDataTable = newArr // arr
    },
    // 取得 left Chart data
    getLeftData (self) {
      var code = self.reportTitle.left.code // 2-1
      var preChartData = []
      var total = 0
      self.entityListGroupBy.forEach(data => {
        if (data.code.substring(0, 3) === code) {
          var arr = []
          arr.push(data.debit)
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
      self.reportTitle.left.total = total
    },
    // 取得 right Chart data
    getRightData (self) {
      var code = self.reportTitle.right.code // 2-2
      var preChartData = []
      var total = 0
      self.entityListGroupBy.forEach(data => {
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
      self.reportTitle.right.total = total
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
      // var size = arr.length
      // for (var i = 0; i < size; i++) {
      //   var i2 = i + 1
      //   if (i2 !== size) {
      //     var num1 = self.subjectToNumber(arr[i][0][0])
      //     var num2 = self.subjectToNumber(arr[i2][0][0])
      //     if (num1 > num2) {
      //       var temp = arr[i]
      //       arr[i] = arr[i2]
      //       arr[i2] = temp
      //     }
      //   }
      // }
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
