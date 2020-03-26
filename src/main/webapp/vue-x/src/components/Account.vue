<template>
  <div>
    <div style="padding-top: 1%">
      <div>
        <h1>Accounts</h1>
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
            <loading :display="display" :status="loadingCode" />
          </span>
          <div v-if="display === false">
            <table class="table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Account</th>
                  <th>Status</th>
                  <th>Error Times</th>
                  <th>Member ID</th>
                  <th>Last Login</th>
                  <th>Time Modify</th>
                  <th>Operate</th>
                </tr>
                <tr>
                  <th></th>
                  <th><input v-model="entity.account" @change="queryEntity()" type="text" class="form-control" placeholder="請輸入..."></th>
                  <th>
                    <select class="custom-select" v-model="entity.status" @change="queryEntity()">
                      <option v-for="(option, index) in PARAMS.Status" :key="index" :value="option.value">{{ option.text }}</option>
                    </select>
                  </th>
                  <th></th>
                  <th></th>
                  <th></th>
                  <th></th>
                  <th>
                    <button type="button" class="btn btn-primary" @click="mainFunction('add', null)">Add</button>
                    &nbsp;
                    <button type="button" class="btn btn-outline-info">Search</button>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(bean, index) in entityList" :key="index">
                  <td>{{ bean.id }}</td>
                  <td>{{ bean.account }}</td>
                  <td>{{ getStatusText(bean.status) }}</td>
                  <td>{{ bean.errorTimes }}</td>
                  <td>{{ getMemberName(bean.memberId) }}</td>
                  <td>{{ toFormatDateTime(bean.timeLast) }}</td>
                  <td>{{ toFormatDateTime(bean.timeModify) }}</td>
                  <td>
                    <button type="button" class="btn btn-outline-primary" @click="mainFunction('edit', bean)">Edit</button>
                    <button type="button" class="btn btn-outline-danger" @click="mainFunction('remove', bean)">Remove</button>
                    <button type="button" class="btn btn-outline-primary" v-if="bean.status === PARAMS.Status.lock.value">Unlock</button>
                    <button type="button" class="btn btn-outline-primary" v-if="bean.status === PARAMS.Status.disable.value">Enable</button>
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
              <label for="account">Account</label>
              <input v-model="entity.account" type="text" class="form-control is-invalid" id="account" placeholder="Account" required>
              <div v-if="!validate.account" class="invalid-feedback">不可空白</div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <button class="btn btn-outline-secondary btn btn-info"
                    style="color: white" type="button" @click="validateAccount()">帳號同名驗證</button>
                </div>
                <input v-if="validate.sameName" style="color: green"
                  type="text" class="form-control" value="驗證成功" aria-label="Example text with button addon" readonly>
                <input v-if="!validate.sameName" style="color: red"
                  type="text" class="form-control" value="驗證失敗, 請換一個帳號" aria-label="Example text with button addon" readonly>
              </div>
            </div>

            <div>
              <label for="password">Password</label>
              <input v-model="entity.password" type="text" class="form-control is-invalid" id="password" placeholder="請輸入密碼" required>
              <div v-if="!validate.password" class="invalid-feedback">不可空白</div>
              <div class="input-group mb-3">
                <input v-model="entity.passwordAgain" type="text" class="form-control" placeholder="再次輸入密碼" required>
                <div class="input-group-append">
                  <span v-if="validate.correctPassword" class="input-group-text" style="color: green">密碼正確</span>
                  <span v-if="!validate.correctPassword" class="input-group-text" style="color: red">請再檢查一次, 密碼錯誤</span>
                </div>
              </div>
            </div>
            <br />

            <div>
              <label for="member">Member</label>
              <div class="form-group">
                <select class="custom-select" id="member" v-model="entity.memberId" required>
                  <option v-for="(m, index) in memberList" :key="index" :value="m.id">{{ m.name }}</option>
                </select>
                <div v-if="!validate.member" class="invalid-feedback">請選擇所屬會員</div>
              </div>
            </div>

            <div>
              <label for="status">Status</label>
              <div class="form-group">
                <select class="custom-select" id="status" v-model="entity.status" required>
                  <option v-for="(option, index) in PARAMS.Status" :key="index" :value="option.value">{{ option.text }}</option>
                </select>
                <div v-if="!validate.status" class="invalid-feedback">請選擇帳號狀態</div>
              </div>
            </div>

            <div>
              <label for="errorTimes">Error Times</label>
              <input v-model="entity.errorTimes" type="number" class="form-control" id="errorTimes" placeholder="(empty)" required readonly>
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
  name: 'Account',
  components: {
    'loading': loading
  },
  data () {
    return {
      API: {
        entityName: 'account',
        query: {
          method: 'post',
          url: '/onAccountX/srv/account'
        },
        create: {
          method: 'put',
          url: '/onAccountX/srv/account'
        },
        update: {
          method: 'put',
          url: '/onAccountX/srv/account/' // +id
        },
        delete: {
          method: 'delete',
          url: '/onAccountX/srv/account/' // +id
        },
        queryMemberName: {
          method: 'get',
          url: '/onAccountX/srv/member/list/name' // only id & name
        },
        validateAccount: {
          method: 'get',
          url: '/onAccountX/srv/account/list/account' // only id & account
        }
      },
      PARAMS: {
        Layout: {
          Manage: { value: 0, text: '帳戶維護' },
          Add: { value: 1, text: '帳戶新增' },
          Edit: { value: 2, text: '帳戶編輯' }
        },
        Status: {
          default: { value: null, text: '請選擇...' },
          disable: { value: 0, text: '停用' },
          enable: { value: 1, text: '啟用中' },
          lock: { value: 4, text: '已鎖定' }
        }
      },
      entity: {
        id: -1,
        account: '',
        password: '',
        passwordAgain: '',
        memberId: -1,
        status: null, // ui 顯示 "請選擇..."
        errorTimes: 0,
        timeLast: -1,
        timeModify: -1
      },

      // validate option
      validate: {
        sameName: false,
        account: false,
        password: false,
        passwordAgain: false,
        correctPassword: false,
        memberId: false,
        status: false
      },
      validateFinal: false,

      entityList: [],
      memberList: [{
        id: null, // ui 顯示 "請選擇..."
        name: '請選擇...'
      }],

      // layout
      thisLayout: 0,

      // loading
      display: true,
      loadingCode: 0
    }
  },
  mounted () {
    this.queryEntity()
    this.queryMemberName()
  },
  updated () {
    if ((this.thisLayout === this.PARAMS.Layout.Add.value) || (this.thisLayout === this.PARAMS.Layout.Edit.value)) {
      var final = true
      var v = this.validate
      var e = this.entity
      v.account = e.account !== ''
      v.memberId = (e.memberId !== null) && (e.status !== -1)
      v.password = (e.password !== null) && (e.password !== '')
      v.passwordAgain = (e.passwordAgain !== null) && (e.passwordAgain !== '')
      v.correctPassword = v.password && v.passwordAgain && (e.password === e.passwordAgain)
      v.status = (e.status !== null) && (e.status !== -1)
      for (var item in v) {
        if (!v[item]) {
          final = false
        }
      }
      this.validateFinal = final
    }
  },
  methods: {
    /* About UI */
    initData () {
      this.entity = {
        id: -1,
        account: '',
        password: '',
        passwordAgain: '',
        memberId: null,
        status: null,
        errorTimes: 0
      }
      this.validate = {
        sameName: false,
        account: false,
        password: false,
        passwordAgain: false,
        correctPassword: false,
        memberId: false,
        status: false
      }
      this.validateFinal = false
    },
    editBean (bean) { // 編輯時回填資料
      this.entity = {
        id: bean.id,
        account: bean.account,
        memberId: bean.memberId,
        status: bean.status,
        errorTimes: bean.errorTimes
      }
    },
    getMemberName (id) {
      var name = ''
      this.memberList.forEach(m => {
        if (m.id === id) {
          name = m.name
        }
      })
      return name
    },
    getStatusText (value) {
      var str = 'Error'
      switch (value) {
        case this.PARAMS.Status.disable.value:
          str = this.PARAMS.Status.disable.text
          break
        case this.PARAMS.Status.enable.value:
          str = this.PARAMS.Status.enable.text
          break
        case this.PARAMS.Status.lock.value:
          str = this.PARAMS.Status.lock.text
          break
      }
      return str
    },
    /* About API */
    queryBean () { // filter 資料用
      return {
        account: (this.entity.account !== '') ? this.entity.account : '',
        status: (this.entity.status !== '') ? this.entity.status : null
      }
    },
    saveBean (bean) { // create, update 資料用
      return {
        id: bean.id,
        account: bean.account,
        password: bean.password,
        memberId: bean.memberId,
        status: bean.status,
        errorTimes: bean.errorTimes
      }
    },
    pkgApiEntity (apiBean) { // 打包成 API 用 Entity
      return {
        account: apiBean
      }
    },
    validateAccount () { // 驗證有無同名帳號
      var b = true
      var self = this
      axios({
        method: this.API.validateAccount.method,
        url: this.API.validateAccount.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {}
      }).then(function (response) {
        if (response) {
          var list = response.data.data
          // 驗證
          if (self.entity.account) {
            list.forEach(acc => {
              if (acc.account === self.entity.account) {
                b = false
              }
            })
          } else {
            b = false
          }
          self.validate.sameName = b
        }
      }).catch(function (error) {
        console.log('>>> Error: validate ' + this.API.entityName + ' failed: ', error)
      })
    },
    queryMemberName () {
      var self = this
      axios({
        method: this.API.queryMemberName.method,
        url: this.API.queryMemberName.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {}
      }).then(function (response) {
        if (response) {
          self.memberList = self.memberList.concat(response.data.data)
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query member failed: ', error)
      })
    },

    /* CRUD API - 以下不必修改 */
    mainFunction (slosilo, bean) {
      var self = this
      var layout = this.PARAMS.Layout
      var result = false
      if (slosilo) {
        switch (slosilo) {
          case 'add':
            self.initData()
            self.thisLayout = layout.Add.value
            break
          case 'finish':
            result = self.createEntity(self.entity)
            if (result) {
              self.thisLayout = layout.Manage.value
              self.initData()
            }
            break
          case 'edit':
            self.editBean(bean)
            self.thisLayout = layout.Edit.value
            break
          case 'update':
            result = self.updateEntity(self.entity)
            if (result) {
              self.thisLayout = layout.Manage.value
              self.initData()
            }
            break
          case 'cancel':
            self.thisLayout = layout.Manage.value
            self.initData()
            break
          case 'remove':
            if (confirm('確定要刪除 id: ' + bean.id + ' ?')) {
              self.deleteEntity(bean.id)
              self.initData()
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
    queryEntity () {
      var self = this
      var apiBean = self.queryBean()
      axios({
        method: this.API.query.method,
        url: this.API.query.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: this.pkgApiEntity(apiBean)
      }).then(function (response) {
        if (response) {
          self.entityList = response.data.data
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query ' + this.API.entityName + ' failed: ', error)
      })
    },
    createEntity (bean) {
      var self = this
      var apiBean = this.saveBean(bean)
      var result = false
      /*
      for (var att in self.validate) {
        console.log(att + ':' + self.validate[att])
      }
      console.log('validate: ', self.validateFinal)
      */
      if (self.validateFinal) {
        result = axios({
          method: this.API.create.method,
          url: this.API.create.url,
          headers: {
            'Content-Type': 'application/json',
            'mac': 'helloJWT'
          },
          data: this.pkgApiEntity(apiBean)
        }).then(function (response) {
          if (response) {
            self.queryEntity()
          }
          return true
        }).catch(function (error) {
          console.log('>>> Error: Add failed: ', error)
          return false
        })
      } else {
        alert('>>> 請檢查是否有欄位未填寫 / 帳號名稱未驗證')
        result = false
      }
      return result
    },
    updateEntity (bean) {
      var self = this
      var apiBean = this.saveBean(bean)
      var result = false
      if (self.validateFinal) {
        axios({
          method: this.API.update.method,
          url: this.API.update.url + apiBean.id,
          headers: {
            'Content-Type': 'application/json',
            'mac': 'helloJWT'
          },
          data: this.pkgApiEntity(apiBean)
        }).then(function (response) {
          if (response) {
            self.queryEntity()
          }
          result = true
        }).catch(function (error) {
          console.log('>>> Error: Edit ' + this.API.entityName + ' failed: ', error)
        })
      }
      return result
    },
    deleteEntity (id) {
      var self = this
      axios({
        method: this.API.delete.method,
        url: this.API.delete.url + id,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        }
      }).then(function (response) {
        if (response) {
          self.queryEntity()
        }
      }).catch(function (error) {
        console.log('>>> Error: Delete ' + this.API.entityName + ' failed: ', error)
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
