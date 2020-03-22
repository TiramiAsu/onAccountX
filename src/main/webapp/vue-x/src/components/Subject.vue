<template>
  <div>
    <div style="padding-top: 1%">
      <div>
        <h1>Accounting Subjects</h1>
        <h6 style="color: lightgray">
          <span v-if="thisLayout === PARAMS.Layout.Manage.value">{{ PARAMS.Layout.Manage.text }}</span>
          <span v-if="thisLayout === PARAMS.Layout.Add.value">{{ PARAMS.Layout.Add.text }}</span>
          <span v-if="thisLayout === PARAMS.Layout.Edit.value">{{ PARAMS.Layout.Edit.text }}</span>
        </h6>
      </div>
      <br />
      <div>
        <!-- Manage UI -->
        <div v-if="thisLayout === PARAMS.Layout.Manage.value">
          <span>
            <loading :display="display" :code="loadingCode" />
          </span>
          <div v-if="display === false">
            <table class="table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Code</th>
                  <th>Name</th>
                  <th>Time Modify</th>
                  <th>Operate</th>
                </tr>
                <tr>
                  <th></th>
                  <th><input v-model="subject.code" @change="querySubject()" type="text" class="form-control"></th>
                  <th><input v-model="subject.name" @change="querySubject()" type="text" class="form-control"></th>
                  <th></th>
                  <th>
                    <button type="button" class="btn btn-primary" @click="mainFunction('add', null)">Add</button>
                    &nbsp;
                    <button type="button" class="btn btn-outline-info">Search</button>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(bean, index) in subjectList" :key="index">
                  <td>{{ bean.id }}</td>
                  <td>{{ bean.code }}</td>
                  <td>{{ bean.name }}</td>
                  <td>{{ toFormatDateTime(bean.timeModify) }}</td>
                  <td>
                    <button type="button" class="btn btn-outline-primary" @click="mainFunction('edit', bean)">Edit</button>
                    <button type="button" class="btn btn-outline-danger" @click="mainFunction('remove', bean)">Remove</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Add/Edit UI -->
        <div v-if="thisLayout !== PARAMS.Layout.Manage.value">
          <form class="was-validated">

            <div>
              <label for="subjectCode">Code</label>
              <input v-model="subject.code" type="text" class="form-control is-invalid" id="subjectCode" placeholder="x-x-x-x..." required>
              <div v-if="validate.code" class="invalid-feedback">不可空白</div>
            </div>
            <br />

            <div>
              <label for="subjectName">Subject Name</label>
              <input v-model="subject.name" type="text" class="form-control is-invalid" id="subjectName" placeholder="Name" required>
              <div v-if="validate.name" class="invalid-feedback">不可空白</div>
            </div>
            <br />

          </form>
          <button type="button" class="btn btn-outline-dark" @click="mainFunction('cancel', null)">Cancel</button>
          <button v-if="thisLayout === PARAMS.Layout.Add.value"
                  type="button" class="btn btn-outline-primary" @click="mainFunction('finish', null)">Finish</button>
          <button v-if="thisLayout === PARAMS.Layout.Edit.value"
                  type="button" class="btn btn-outline-primary" @click="mainFunction('update', null)">Update</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import loading from './Loading.vue'

export default {
  name: 'Subject',
  components: {
    'loading': loading
  },
  data () {
    return {
      PARAMS: {
        Layout: {
          Manage: { value: 0, text: '帳務科目維護' },
          Add: { value: 1, text: '帳務科目新增' },
          Edit: { value: 2, text: '帳務科目編輯' }
        }
      },
      subject: {
        id: 0,
        code: '',
        name: '',
        timeModify: 0
      },
      subjectList: [],

      // layout
      thisLayout: 0,

      // loading
      display: true,
      loadingCode: 0,

      // CSS
      validate: {
        code: false,
        name: false
      }
    }
  },
  mounted () {
    this.querySubject()
  },
  updated () {
    this.validate.code = !(this.subject.code !== '')
    this.validate.name = !(this.subject.name !== '')
  },
  methods: {
    mainFunction (slosilo, bean) {
      var self = this
      var layout = this.PARAMS.Layout
      if (slosilo) {
        switch (slosilo) {
          case 'add':
            self.initBean()
            self.thisLayout = layout.Add.value
            break
          case 'finish':
            self.createSubject(self.subject)
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'edit':
            self.subject.id = bean.id
            self.subject.code = bean.code
            self.subject.name = bean.name

            self.thisLayout = layout.Edit.value
            break
          case 'update':
            self.updateSubject(self.subject)
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'cancel':
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'remove':
            if (confirm('確定要刪除 id: ' + bean.id + ' ?')) {
              self.deleteSubject(bean.id)
              self.initBean()
            } else {
              alert('已取消刪除 id: ' + bean.id)
            }
            break
          case 'search':
            break
        }
      } else {
        console.log('>>> Error, slosilo is null <<<')
      }
    },
    initBean () {
      this.subject = {
        id: 0,
        code: '',
        name: '',
        timeModify: 0
      }
    },
    toApiBean (bean) {
      return {
        id: bean.id,
        code: bean.code,
        name: bean.name
      }
    },
    // 頁面載入完, 執行方法檢查是否有資訊
    // doRemove (id) {
    //   if (confirm('是否要刪除 id ' + id)) {
    //     window.location.href = 'subject?action=remove&id=' + id
    //   }
    // },
    querySubject () {
      var self = this
      var apiBean = {
        code: (self.subject.code !== '') ? self.subject.code : '',
        name: (self.subject.name !== '') ? self.subject.name : ''
      }
      axios({
        method: 'post',
        url: '/onAccountX/srv/subject',
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          subject: apiBean
        }
      }).then(function (response) {
        if (response) {
          self.subjectList = response.data.data
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query subject failed: ', error)
      })
    },
    createSubject (bean) {
      var self = this
      var apiBean = this.toApiBean(bean)
      axios({
        method: 'put',
        url: '/onAccountX/srv/subject',
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          Subject: apiBean
        }
      }).then(function (response) {
        if (response) {
          self.querySubject()
        }
      }).catch(function (error) {
        console.log('>>> Error: Add subject failed: ', error)
      })
    },
    updateSubject (bean) {
      var self = this
      var apiBean = this.toApiBean(bean)
      axios({
        method: 'put',
        url: '/onAccountX/srv/subject/' + apiBean.id,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          Subject: apiBean
        }
      }).then(function (response) {
        if (response) {
          self.querySubject()
        }
      }).catch(function (error) {
        console.log('>>> Error: Edit subject failed: ', error)
      })
    },
    deleteSubject (id) {
      var self = this
      axios({
        method: 'delete',
        url: '/onAccountX/srv/subject/' + id,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {}
      }).then(function (response) {
        if (response) {
          self.querySubject()
        }
      }).catch(function (error) {
        console.log('>>> Error: Delete subject failed: ', error)
      })
    },

    /* 時間格式 */

    // 取得格式化(YYY-MM-DD HH:mm)後的日期時間字串
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
</style>
