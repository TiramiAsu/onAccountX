<template>
  <div>
    <div style="padding-top: 1%">
      <div>
        <h1>Members</h1>
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
                  <th>Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Time Modify</th>
                  <th>Operate</th>
                </tr>
                <tr>
                  <th></th>
                  <th>
                    <input type="text" class="form-control" placeholder="請輸入..."
                      v-model="entity.name" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
                  </th>
                  <th>
                    <input type="text" class="form-control" placeholder="請輸入..."
                      v-model="entity.email" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
                  </th>
                  <th>
                    <input type="text" class="form-control" placeholder="請輸入..."
                      v-model="entity.phone" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
                  </th>
                  <th></th>
                  <th>
                    <button type="button" class="btn btn-primary" @click="toLayout(PARAMS.Layout.Add.symbol)">Add</button>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(bean, index) in entityList" :key="index">
                  <td>{{ bean.id }}</td>
                  <td>{{ bean.name }}</td>
                  <td>{{ bean.email }}</td>
                  <td>{{ bean.phone }}</td>
                  <td>{{ toFormatDateTime(bean.timeModify) }}</td>
                  <td>
                    <button type="button" class="btn btn-outline-primary btn-circle" title="Edit"
                      @click="toLayout(PARAMS.Layout.Edit.symbol, bean)"><i class="material-icons">create</i></button>

                    <button type="button" class="btn btn-outline-danger btn-circle" title="Remove"
                      @click="deleteEntity(bean.id)"><i class="material-icons">delete</i></button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Add/Edit UI -->
        <div v-if="thisLayout !== PARAMS.Layout.Manage.value">
          <form class="was-validated">

            <!-- Name -->
            <div>
              <label for="memberName">Member Name</label>
              <input v-model="entity.name" type="text" class="form-control is-invalid" id="memberName" placeholder="Name" required>
              <div v-if="!validate.name" class="invalid-feedback">不可空白</div>
            </div>
            <br />

            <!-- Email -->
            <div v-if="thisLayout === PARAMS.Layout.Add.value">
              <label for="memberEmail">Email</label>
              <input v-model="entity.email" type="text" class="form-control is-invalid" id="memberEmail"
                placeholder="example@gmail.com" @change="validate.email = false" required>
              <div v-if="!validate.email" class="invalid-feedback">不可空白 / 格式錯誤</div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <button class="btn btn-outline-secondary btn btn-info"
                    style="color: white" type="button" @click="validateEmail()">Email 註冊驗證</button>
                </div>
                <input v-if="validate.sameEmail" style="color: green"
                  type="text" class="form-control" value="驗證成功, 此 Email 可使用" aria-label="Example text with button addon" readonly>
                <input v-if="!validate.sameEmail" style="color: red"
                  type="text" class="form-control" value="驗證失敗, 此 Email 格式錯誤 或 已被註冊" aria-label="Example text with button addon" readonly>
              </div>
            </div>
            <div v-if="thisLayout === PARAMS.Layout.Edit.value">
              <label>Email</label>
              <input v-model="entity.email" type="text" class="form-control" placeholder="example@gmail.com" readonly>
              <br />
            </div>

            <!-- Phone -->
            <div v-if="thisLayout === PARAMS.Layout.Add.value">
              <label for="memberPhone">Phone</label>
              <input v-model="entity.phone" type="text" class="form-control is-invalid" id="memberPhone"
                placeholder="Phone" @change="validate.samePhone = false" required>
              <div v-if="!validate.phone" class="invalid-feedback">不可空白 / 格式錯誤</div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <button class="btn btn-outline-secondary btn btn-info"
                    style="color: white" type="button" @click="validatePhone()">Phone 登錄驗證</button>
                </div>
                <input v-if="validate.samePhone" style="color: green"
                  type="text" class="form-control" value="驗證成功, 此號碼可使用" aria-label="Example text with button addon" readonly>
                <input v-if="!validate.samePhone" style="color: red"
                  type="text" class="form-control" value="驗證失敗, 此號碼格式錯誤 或 已被登錄" aria-label="Example text with button addon" readonly>
              </div>
            </div>
            <div v-if="thisLayout === PARAMS.Layout.Edit.value">
              <label>Phone</label>
              <input v-model="entity.phone" type="text" class="form-control" placeholder="Phone" readonly>
              <br />
            </div>

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
  name: 'Member',
  components: {
    'loading': loading
  },
  data () {
    return {
      API: {
        entityName: 'member',
        query: {
          method: 'post',
          url: '/onAccountX/srv/member'
        },
        create: {
          method: 'put',
          url: '/onAccountX/srv/member'
        },
        update: {
          method: 'put',
          url: '/onAccountX/srv/member/' // +id
        },
        delete: {
          method: 'delete',
          url: '/onAccountX/srv/member/' // +id
        },
        validateEmail: {
          method: 'post',
          url: '/onAccountX/srv/member/validate/email'
        },
        validatePhone: {
          method: 'post',
          url: '/onAccountX/srv/member/validate/phone'
        }
      },
      PARAMS: {
        Layout: {
          Manage: { symbol: 'manage', value: 0, text: '會員維護' },
          Add: { symbol: 'add', value: 1, text: '會員新增' },
          Edit: { symbol: 'edit', value: 2, text: '會員編輯' },
          Action: {
            Filter: { symbol: 'filter', value: 8 },
            Cancel: { symbol: 'cancel', value: 9 }
          }
        }
      },
      entity: {
        // Member Object
        id: -1,
        name: '',
        email: '',
        phone: '',
        timeModify: 0
      },

      // validate option
      validate: {
        // empty
        name: false,
        email: false,
        phone: false,
        // function
        sameEmail: false, // 檢查 Email 是否註冊
        samePhone: false // 檢查 Phone 是否登錄
      },
      validateFinal: false, // 檢查結果

      entityList: [],
      message: '',

      // layout
      thisLayout: 0,

      // loading
      display: true,
      loadingCode: 0
    }
  },
  mounted () {
    this.queryEntity()
  },
  updated () {
    var v = this.validate
    var e = this.entity
    // empty
    v.name = e.name !== ''
    v.email = (e.email !== '') && (/\S+@\S+\.\S+/.test(e.email))
    v.phone = (e.phone !== '') && (/[0]{1}\d{9}/.test(e.phone)) && (e.phone.length === 10)
    console.log(e.phone !== '')
    console.log(/[0]{1}\d{9}/.test(e.phone))
    console.log(e.phone.length === 10)
    console.log('---')
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
              // Member Object
              id: bean.id,
              name: bean.name,
              email: bean.email,
              phone: bean.phone
            }
          } else {
            console.log(`>>> Error: 'bean' is not defined.`)
          }
          break
        // 其餘頁面
        default:
          self.entity = {
            // Member Object
            id: -1,
            name: '',
            email: '',
            phone: ''
          }
          break
      }
      self.validate = {
        // empty
        name: false,
        email: false,
        phone: false,
        // other
        sameEmail: false,
        samePhone: false
      }
      self.validateFinal = false
    },

    /* Bean */

    // 查詢欄位 -> filter 資料用
    queryBean (bean) {
      var apiBean = {
        name: '',
        email: '',
        phone: ''
      }
      if (bean) {
        apiBean = {
          name: (bean.name !== '') ? bean.name : '',
          email: (bean.email !== '') ? bean.email : '',
          phone: (bean.phone !== '') ? bean.phone : ''
        }
      }
      return apiBean
    },
    // 新增修改資料 -> create, update 資料用
    saveBean (bean) {
      return {
        id: bean.id,
        name: bean.name,
        email: bean.email,
        phone: bean.phone
      }
    },
    // 打包 API 用 Entity
    pkgApiEntity (apiBean) {
      return {
        member: apiBean
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
    createEntity (bean) {
      var self = this
      var v = this.validate
      var apiBean = self.saveBean(bean)

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
            alert('新增完成')
          }
        }).catch(function (error) {
          console.log('>>> Error: Add ' + self.API.entityName + ' failed: ', error)
        })
      } else {
        alert('請檢查是否有欄位未填寫 / Email, Phone 未驗證')
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
            self.queryEntity()
            alert('更新完成')
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
          },
          data: {}
        }).then(function (response) {
          if (response) {
            if (response.data.statusCode === 595) {
              alert('此會員有連接帳號, 請清除完所有連接帳號即可刪除')
            } else {
              self.queryEntity()
              alert('刪除完成')
            }
          }
        }).catch(function (error) {
          console.log('>>> Error: Delete ' + self.API.entityName + ' failed: ', error)
        })
      }
    },

    /* API - other */

    // 驗證 Email 有無註冊過
    validateEmail () {
      var self = this
      axios({
        method: self.API.validateEmail.method,
        url: self.API.validateEmail.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          email: self.entity.email
        }
      }).then(function (response) {
        var b = true
        console.log(response)
        if (response) {
          b = response.data.result
        } else {
          b = false // response 有問題
        }
        self.validate.sameEmail = b
      }).catch(function (error) {
        console.log('>>> Error: validate ' + self.API.entityName + ' Email failed: ', error)
        self.validate.sameEmail = false
      })
    },
    // 驗證 Phone 有無登錄過
    validatePhone () {
      var self = this
      console.log(self.entity.phone)
      axios({
        method: self.API.validatePhone.method,
        url: self.API.validatePhone.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          phone: self.entity.phone
        }
      }).then(function (response) {
        var b = true
        console.log(response)
        if (response) {
          b = response.data.result
        } else {
          b = false // response 有問題
        }
        self.validate.samePhone = b
      }).catch(function (error) {
        console.log('>>> Error: validate ' + self.API.entityName + ' Phone failed: ', error)
        self.validate.samePhone = false
      })
    },

    /* Util - custom */

    // Layout 切換
    toLayout (symbol, bean, actionSymbol) {
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
        if (actionSymbol === self.PARAMS.Layout.Action.Filter.symbol) {
          // filter 存在 -> 不初始化資料
        } else {
          self.initData(self)
        }
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
