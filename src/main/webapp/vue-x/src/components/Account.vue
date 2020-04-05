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
                  <th>Member</th>
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
                    <button type="button" class="btn btn-primary" @click="toLayout(PARAMS.Layout.Add.symbol)">Add</button>
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
                    <button type="button" class="btn btn-outline-primary" @click="toLayout(PARAMS.Layout.Edit.symbol, bean)">Edit</button>
                    <button type="button" class="btn btn-outline-danger" @click="deleteEntity(bean.id)">Remove</button>
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

            <!-- Account -->
            <div v-if="thisLayout === PARAMS.Layout.Add.value">
              <label for="account">Account</label>
              <input v-model="entity.account" type="text" class="form-control is-invalid" id="account"
                placeholder="Account" @change="validate.sameName = false" required>
              <div v-if="!validate.account" class="invalid-feedback">不可空白</div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <button class="btn btn-outline-secondary btn btn-info"
                    style="color: white" type="button" @click="validateAccount()">帳號同名驗證</button>
                </div>
                <input v-if="validate.sameName" style="color: green"
                  type="text" class="form-control" value="驗證成功, 此帳號可使用" aria-label="Example text with button addon" readonly>
                <input v-if="!validate.sameName" style="color: red"
                  type="text" class="form-control" value="驗證失敗, 此帳號已存在" aria-label="Example text with button addon" readonly>
              </div>
            </div>
            <div v-if="thisLayout === PARAMS.Layout.Edit.value">
              <label>Account</label>
              <input v-model="entity.account" type="text" class="form-control" placeholder="Account" readonly>
              <br />
            </div>

            <!-- Password -->
            <div>
              <label for="password">Password</label>
              <!-- change password -->
              <div v-if="thisLayout === PARAMS.Layout.Edit.value">
                <div class="input-group">
                  <div class="input-group-prepend input-group-text">
                    <input type="radio" name="changePassword"
                      v-model="changePassword" :value="true" aria-label="Radio button for following text input">
                  </div>
                  <input type="text" class="form-control" value="變更密碼" readonly />
                </div>
                <div class="input-group">
                  <div class="input-group-prepend input-group-text">
                    <input type="radio" name="changePassword"
                      v-model="changePassword" :value="false" aria-label="Radio button for following text input">
                  </div>
                  <input type="text" class="form-control" value="不變更密碼" readonly />
                </div>
                <br />
              </div>

              <div v-if="thisLayout === PARAMS.Layout.Add.value">
                <input v-model="entity.password" type="text"
                  class="form-control is-invalid" id="password" placeholder="請輸入密碼" required>
                <div v-if="!validate.password" class="invalid-feedback">不可空白</div>
                <div class="input-group mb-3">
                  <input v-model="entity.passwordAgain" type="text"
                    class="form-control" placeholder="再次輸入密碼" required>
                  <div class="input-group-append">
                    <span v-if="validate.correctPassword" class="input-group-text" style="color: green">密碼正確</span>
                    <span v-if="!validate.correctPassword" class="input-group-text" style="color: red">請再檢查一次, 密碼錯誤</span>
                  </div>
                </div>
              </div>
              <div v-if="(thisLayout === PARAMS.Layout.Edit.value) && changePassword">
                <input v-model="entity.password" type="text"
                  class="form-control is-invalid" id="password" placeholder="請輸入新密碼" required>
                <div v-if="!validate.password" class="invalid-feedback">不可空白</div>
                <div class="input-group mb-3">
                  <input v-model="entity.passwordAgain" type="text"
                    class="form-control" placeholder="再次輸入新密碼" required>
                  <div class="input-group-append">
                    <span v-if="validate.correctPassword" class="input-group-text" style="color: green">密碼正確</span>
                    <span v-if="!validate.correctPassword" class="input-group-text" style="color: red">請再檢查一次, 密碼錯誤</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Member -->
            <div>
              <label for="member">Member</label>
              <div class="form-group">
                <select class="custom-select" id="member" v-model="entity.memberId" required>
                  <option v-for="(m, index) in memberList" :key="index" :value="m.id">{{ m.name }}</option>
                </select>
                <div v-if="!validate.member" class="invalid-feedback">請選擇所屬會員</div>
              </div>
            </div>

            <!-- Status -->
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

            <!-- update validate user-->
            <div v-if="thisLayout === PARAMS.Layout.Edit.value">
              <label for="oldPasswordAgain">Validate</label>
              <input v-model="entity.oldPasswordAgain" type="text" class="form-control is-invalid" id="oldPasswordAgain"
                placeholder="請輸入舊密碼" @change="validate.oldPasswordAgain = false" required>
              <div v-if="!validate.oldPasswordAgain" class="invalid-feedback">不可空白</div>
            </div>
            <br />

          </form>
          <button type="button" class="btn btn-outline-dark" @click="toLayout(PARAMS.Layout.Action.Cancel.symbol)">Cancel</button>
          <button v-if="thisLayout === PARAMS.Layout.Add.value"
                  type="button" class="btn btn-outline-primary" @click="createEntity(entity)">Finish</button>
          <button v-if="thisLayout === PARAMS.Layout.Edit.value"
                  type="button" class="btn btn-outline-primary" @click="updateEntity(entity)">Update</button>
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
          Manage: { symbol: 'manage', value: 0, text: '帳戶維護' },
          Add: { symbol: 'add', value: 1, text: '帳戶新增' },
          Edit: { symbol: 'edit', value: 2, text: '帳戶編輯' },
          Action: {
            Cancel: { symbol: 'cancel', value: 9, text: 'Cancel' }
          }
        },
        Status: {
          default: { value: null, text: '請選擇...' },
          disable: { value: 0, text: '停用' },
          enable: { value: 1, text: '啟用中' },
          lock: { value: 4, text: '已鎖定' }
        }
      },
      entity: {
        // Account Object
        id: -1,
        account: '',
        password: '',
        memberId: null,
        status: null, // ui 顯示 "請選擇..."
        errorTimes: 0,
        timeLast: -1,
        timeModify: -1,
        // other
        passwordAgain: '', // 密碼驗證
        // edit -> 編輯時所需驗證資料
        oldPasswordAgain: '' // 輸入舊密碼 -> 由 API 驗證
      },

      // validate option
      validate: {
        // empty
        account: false,
        password: false, // 新密碼
        memberId: false,
        status: false,
        // empty -> other
        passwordAgain: false, // 新密碼驗證
        oldPasswordAgain: false, // 舊密碼驗證
        // function
        sameName: false, // 檢查同名帳號
        correctPassword: false // 檢查新密碼輸入
      },
      changePassword: false, // true: 變更密碼 / false: 不變更密碼
      validateFinal: false, // 檢查結果

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
    var v = this.validate
    var e = this.entity
    // [add]
    if (this.thisLayout === this.PARAMS.Layout.Add.value) {
      v.account = e.account !== ''
      // v.sameName 由 validateAccount() 處理
      v.password = (e.password !== null) && (e.password !== '')
      v.passwordAgain = (e.passwordAgain !== null) && (e.passwordAgain !== '')
      v.correctPassword = v.password && v.passwordAgain && (e.password === e.passwordAgain)
      v.oldPasswordAgain = true
    }
    // [edit]
    if (this.thisLayout === this.PARAMS.Layout.Edit.value) {
      v.account = true
      v.sameName = true
      // 是否變更密碼
      if (this.changePassword) {
        // 變更密碼 -> 新密碼
        v.password = (e.password !== null) && (e.password !== '')
        v.passwordAgain = (e.passwordAgain !== null) && (e.passwordAgain !== '')
        v.correctPassword = v.password && v.passwordAgain && (e.password === e.passwordAgain)
      } else {
        // 不改密碼
        v.password = true
        v.passwordAgain = true
        v.correctPassword = true
      }
      v.oldPasswordAgain = (e.oldPasswordAgain !== null) && (e.oldPasswordAgain !== '')
    }
    v.memberId = (e.memberId !== null) && (e.status !== -1)
    v.status = (e.status !== null) && (e.status !== -1)
    for (var item in v) {
      if (!v[item]) {
        // console.log(item + ':' + v[item])
      }
    }
  },
  methods: {
    /* Initial */

    // [初始化資料] 每次切換頁面
    initData (self, bean) {
      switch (self.thisLayout) {
        // 編輯頁面 -> 需回填資料
        case self.PARAMS.Layout.Edit.value:
          if (bean) {
            self.entity = {
              // Account Object
              id: bean.id,
              account: bean.account,
              password: '',
              memberId: bean.memberId,
              status: bean.status,
              errorTimes: bean.errorTimes,
              timeLast: -1, // 由 API 處理
              timeModify: -1, // 由 API 處理
              // other
              passwordAgain: '',
              // edit
              oldPasswordAgain: ''
            }
          } else {
            console.log(`>>> Error: 'bean' is not defined.`)
          }
          break
        // 其餘頁面
        default:
          self.entity = {
            // Account Object
            id: -1,
            account: '',
            password: '',
            memberId: null,
            status: null,
            errorTimes: 0,
            timeLast: -1, // 不處理
            timeModify: -1, // 不處理
            // other
            passwordAgain: '',
            // edit
            oldPasswordAgain: ''
          }
          break
      }
      self.validate = {
        // empty
        account: false,
        password: false,
        memberId: false,
        status: false,
        // empty -> other
        passwordAgain: false,
        oldPasswordAgain: false,
        // 編輯驗證資料
        sameName: false,
        correctPassword: false
      }
      self.changePassword = false // 預設不改密碼
      self.validateFinal = false
    },

    /* Bean */

    // 查詢欄位 -> filter 資料用
    queryBean (bean) {
      var apiBean = {
        account: '',
        status: null
      }
      if (bean) {
        apiBean = {
          account: (bean.account !== '') ? bean.account : '',
          status: (bean.status !== '') ? bean.status : null
        }
      }
      return apiBean
    },
    // 新增修改資料 -> create, update 資料用
    saveBean (bean) {
      return {
        id: bean.id,
        account: bean.account,
        password: bean.password,
        memberId: bean.memberId,
        status: bean.status,
        errorTimes: bean.errorTimes,
        // validate old password
        oldPassword: bean.oldPasswordAgain
      }
    },
    // 打包 API 用 Entity
    pkgApiEntity (apiBean) {
      return {
        account: apiBean
      }
    },

    /* API */

    queryEntity (bean) {
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
        self.toLayout(self.PARAMS.Layout.Manage.symbol)
      }).catch(function (error) {
        console.log('>>> Error: query ' + self.API.entityName + ' failed: ', error)
      })
    },
    createEntity (bean) {
      var self = this
      var apiBean = self.saveBean(bean)
      /* validate */
      // account -> v.sameName
      self.validateAccount()
      // all check
      self.validateData(self.validate)

      // execute
      if (self.validateFinal) {
        axios({
          method: self.API.create.method,
          url: self.API.create.url,
          headers: {
            'Content-Type': 'application/json',
            'mac': 'helloJWT'
          },
          data: self.pkgApiEntity(apiBean)
        }).then(function (response) {
          if (response) {
            self.queryEntity()
          }
        }).catch(function (error) {
          console.log('>>> Error: Add failed: ', error)
        })
      } else {
        alert('請檢查是否有欄位未填寫 / 帳號名稱未驗證')
      }
    },
    updateEntity (bean) {
      var self = this
      var v = this.validate
      var apiBean = this.saveBean(bean)

      /* validate */
      // account -> v.sameName
      v.sameName = true
      // all check
      var final = true
      for (var item in v) {
        if (!v[item]) {
          final = false
        }
      }
      self.validateFinal = final

      // execute
      if (self.validateFinal) {
        // 若不變更密碼, 則將認證密碼設定回原密碼
        if (!self.changePassword) {
          apiBean.password = self.entity.oldPasswordAgain
        }
        axios({
          method: self.API.update.method,
          url: self.API.update.url + apiBean.id,
          headers: {
            'Content-Type': 'application/json',
            'mac': 'helloJWT'
          },
          data: self.pkgApiEntity(apiBean)
        }).then(function (response) {
          if (response) {
            if (response.data.statusCode === 499) {
              alert('驗證身分失敗, 請重新輸入舊密碼')
            } else {
              self.queryEntity()
              alert('更新完成')
            }
          }
        }).catch(function (error) {
          console.log('>>> Error: Edit ' + self.API.entityName + ' failed: ', error)
        })
      }
    },
    deleteEntity (id) {
      var self = this
      if (confirm('確定要刪除 ' + id + ' ?')) {
        axios({
          method: self.API.delete.method,
          url: self.API.delete.url + id,
          headers: {
            'Content-Type': 'application/json',
            'mac': 'helloJWT'
          }
        }).then(function (response) {
          if (response) {
            self.queryEntity()
          }
        }).catch(function (error) {
          console.log('>>> Error: Delete ' + self.API.entityName + ' failed: ', error)
        })
      }
    },

    /* API - other */

    // 取得 member name list -> only id & name
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
    // 資料驗證
    validateData (validate) {
      var self = this
      // 檢查 null, space 由 updated () 即時檢查
      var final = true
      var v = validate

      // total validate
      for (var item in v) {
        if (!v[item]) {
          final = false
          console.log(item + ':' + v[item])
        }
      }
      self.validateFinal = final
    },
    // 驗證有無同名帳號
    validateAccount () {
      var self = this
      axios({
        method: self.API.validateAccount.method,
        url: self.API.validateAccount.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {}
      }).then(function (response) {
        var b = true
        if (response) {
          var list = response.data.data
          // 驗證
          if (self.entity.account) {
            list.forEach(acc => {
              if (acc.account === self.entity.account) {
                b = false
              }
              // console.log(acc.account + ':' + self.entity.account + '=' + b)
            })
          } else {
            b = false // account 欄位有問題
          }
        } else {
          b = false // response 有問題
        }
        self.validate.sameName = b
      }).catch(function (error) {
        console.log('>>> Error: validate ' + self.API.entityName + ' failed: ', error)
        self.validate.sameName = false
      })
    },

    /* Util - custom */

    // 取得 member name by id
    getMemberName (id) {
      var self = this
      var name = ''
      self.memberList.forEach(m => {
        if (m.id === id) {
          name = m.name
        }
      })
      return name
    },
    // 取得 status text by value
    getStatusText (value) {
      var self = this
      var str = 'Error'
      var status = self.PARAMS.Status
      switch (value) {
        case status.disable.value:
          str = status.disable.text
          break
        case status.enable.value:
          str = status.enable.text
          break
        case status.lock.value:
          str = status.lock.text
          break
      }
      return str
    },
    // Layout 切換
    toLayout (symbol, bean) {
      var self = this
      var layout = self.PARAMS.Layout
      var fromLayout = self.thisLayout
      switch (symbol) {
        case layout.Manage.symbol:
          self.thisLayout = layout.Manage.value
          break
        case layout.Action.Cancel.symbol:
          var info = (fromLayout === layout.Add.value)
            ? ('確定取消 ' + layout.Add.text)
            : ('確定取消 ' + layout.Edit.text)
          if (confirm(info + ' ?')) {
            self.thisLayout = layout.Manage.value
          }
          break
        case layout.Edit.symbol:
          self.thisLayout = layout.Edit.value
          break
        case layout.Add.symbol:
          self.thisLayout = layout.Add.value
          break
      }
      // 編輯時需回填資料
      if (self.thisLayout === layout.Edit.value) {
        if (bean) {
          self.initData(self, bean)
        } else {
          console.log(`>>> Error: 'bean' is not defined.`)
        }
      } else {
        self.initData(self)
      }
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
</style>
