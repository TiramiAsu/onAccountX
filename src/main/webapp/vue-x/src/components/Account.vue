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
                  <td>{{ getStatus(bean.status) }}</td>
                  <td>{{ bean.errorTimes }}</td>
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
              <div v-if="validate.account" class="invalid-feedback">不可空白</div>
            </div>
            <br />

            <div>
              <label for="status">Status</label>
              <div class="form-group">
                <select class="custom-select" id="status" v-model="entity.status" required>
                  <option v-for="(option, index) in PARAMS.Status" :key="index" :value="option.value">{{ option.text }}</option>
                </select>
                <div v-if="validate.status" class="invalid-feedback">請選擇帳號狀態</div>
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
          url: '/onAccountX/srv/account' // +id
        },
        delete: {
          method: 'delete',
          url: '/onAccountX/srv/account' // +id
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
        status: null, // ui 顯示 "請選擇..."
        errorTimes: 0,
        timeLast: -1,
        timeModify: -1
      },

      // validate option
      validate: {
        account: false,
        status: false
      },

      entityList: [],

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
    this.validate.account = !(this.entity.account !== '')
    this.validate.status = !(this.entity.status !== null || this.entity.status !== -1)
  },
  methods: {
    initBean () {
      this.entity = {
        id: -1,
        account: '',
        status: null,
        errorTimes: 0
      }
    },
    editBean (bean) {
      this.entity = {
        id: bean.id,
        account: bean.account,
        status: bean.status,
        errorTimes: bean.errorTimes
      }
    },
    getStatus (value) {
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
    // API 用
    filterBean () {
      return {
        account: (this.entity.account !== '') ? this.entity.account : '',
        status: (this.entity.status !== '') ? this.entity.status : null
      }
    },
    toApiBean (bean) {
      return {
        id: bean.id,
        account: bean.account,
        status: bean.status,
        errorTimes: bean.errorTimes
      }
    },
    getApiData (apiBean) {
      return {
        account: apiBean
      }
    },

    /* API - 以下不必修改 */
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
            self.createEntity(self.entity)
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'edit':
            self.editBean(bean)
            self.thisLayout = layout.Edit.value
            break
          case 'update':
            self.updateEntity(self.entity)
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'cancel':
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'remove':
            if (confirm('確定要刪除 id: ' + bean.id + ' ?')) {
              self.deleteEntity(bean.id)
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
    queryEntity () {
      var self = this
      var apiBean = self.filterBean()
      axios({
        method: this.API.query.method,
        url: this.API.query.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: this.getApiData(apiBean)
      }).then(function (response) {
        if (response) {
          self.entityList = response.data.data
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query ' + this.API.entityName + 'failed: ', error)
      })
    },
    createEntity (bean) {
      var self = this
      var apiBean = this.toApiBean(bean)
      axios({
        method: this.API.create.method,
        url: this.API.create.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: this.getApiData(apiBean)
      }).then(function (response) {
        if (response) {
          self.queryEntity()
        }
      }).catch(function (error) {
        console.log('>>> Error: Add failed: ', error)
      })
    },
    updateEntity (bean) {
      var self = this
      var apiBean = this.toApiBean(bean)
      axios({
        method: this.API.update.method,
        url: this.API.update.url + apiBean.id,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: this.getApiData(apiBean)
      }).then(function (response) {
        if (response) {
          self.queryEntity()
        }
      }).catch(function (error) {
        console.log('>>> Error: Edit' + this.API.entityName + 'failed: ', error)
      })
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
        console.log('>>> Error: Delete' + this.API.entityName + 'failed: ', error)
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
