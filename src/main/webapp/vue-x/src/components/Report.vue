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
            <GChart
              type="PieChart"
              :data="chartData"
              :options="chartOptions"
              :resizeDebounce="chartSize"
            />
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
        query: {
          method: 'post',
          url: '/onAccountX/srv/journal'
        }
      },
      PARAMS: {
        Layout: {
          Manage: { symbol: 'manage', value: 0, text: '財務報表' }
        }
      },
      entityList: [],
      accountList: [],
      subjectList: [{
        id: null, // ui 顯示 "請選擇..."
        code: null,
        name: '請選擇...'
      }],
      cashId: -1, // 判斷是否要填現金簿資訊

      // loading
      display: false,
      loadingCode: 0,

      chartData: [
        ['Task', 'Hours per Day'],
        ['Work', 20],
        ['Eat', 2],
        ['Commute', 2],
        ['Watch TV', 2],
        ['Sleep', 7]
      ],
      chartOptions: {
        chart: {
          title: 'My Daily Activities',
          subtitle: 'test'
        }
      },
      chartSize: 600
    }
  },
  mounted () {
  },
  updated () {
  },
  methods: {
    /* Bean */

    // 打包 API 用 Entity
    pkgApiEntity (apiBean) {
      return {
        journal: apiBean
      }
    },
    // 打包 API 用 Entity2
    pkgApiEntity2 (apiBean2) {
      return {
        cashAccount: apiBean2
      }
    },

    /* API */

    queryEntity (bean, actionSymbol) {
      var self = this
      var apiBean = this.queryBean(bean)
      axios({
        method: self.API.query.method,
        url: self.API.query.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: self.pkgApiEntity(apiBean)
      }).then(function (response) {
        if (response) {
          self.entityList = response.data.data
        }
        self.display = false
        self.toLayout(self.PARAMS.Layout.Manage.symbol, null, actionSymbol)
      }).catch(function (error) {
        console.log('>>> Error: query ' + self.API.entityName + ' failed: ', error)
      })
    },

    /* API - other */

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
