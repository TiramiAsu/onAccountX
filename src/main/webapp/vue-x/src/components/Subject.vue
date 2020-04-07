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
                  <th>
                    <input type="text" class="form-control" placeholder="請輸入..."
                      v-model="entity.code" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
                  </th>
                  <th>
                    <input type="text" class="form-control" placeholder="請輸入..."
                      v-model="entity.name" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
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
                  <td>{{ bean.code }}</td>
                  <td>{{ bean.name }}</td>
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

            <!-- Code -->
            <div v-if="thisLayout === PARAMS.Layout.Add.value">
              <label for="subjectCode">Code</label>
              <input v-model="entity.code" type="text" class="form-control is-invalid" id="subjectCode" placeholder="x-x-x-x..." required>
              <div v-if="!validate.code" class="invalid-feedback">不可空白</div>
            </div>
            <div v-if="thisLayout === PARAMS.Layout.Edit.value">
              <label>Code</label>
              <input v-model="entity.code" type="text" class="form-control" placeholder="x-x-x-x..." required readonly>
            </div>
            <br />

            <!-- Name -->
            <div>
              <label for="subjectName">Subject Name</label>
              <input v-model="entity.name" type="text" class="form-control is-invalid" id="subjectName" placeholder="Name" required>
              <div v-if="!validate.name" class="invalid-feedback">不可空白</div>
            </div>
            <br />

          </form>
          <button type="button" class="btn btn-outline-dark" @click="toLayout(PARAMS.Layout.Action.Cancel.symbol)">Cancel</button>

          <button type="button" class="btn btn-outline-primary"
            v-if="thisLayout === PARAMS.Layout.Add.value" @click="createEntity(entity)">Finish</button>

          <button type="button" class="btn btn-outline-primary"
            v-if="thisLayout === PARAMS.Layout.Edit.value" @click="updateEntity(entity)">Update</button>
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
      API: {
        entityName: 'subject',
        query: {
          method: 'post',
          url: '/onAccountX/srv/subject'
        },
        create: {
          method: 'put',
          url: '/onAccountX/srv/subject'
        },
        update: {
          method: 'put',
          url: '/onAccountX/srv/subject/' // +id
        },
        delete: {
          method: 'delete',
          url: '/onAccountX/srv/subject/' // +id
        }
      },
      PARAMS: {
        Layout: {
          Manage: { symbol: 'manage', value: 0, text: '帳務科目維護' },
          Add: { symbol: 'add', value: 1, text: '帳務科目新增' },
          Edit: { symbol: 'edit', value: 2, text: '帳務科目編輯' },
          Action: {
            Filter: { symbol: 'filter' },
            Cancel: { symbol: 'cancel' }
          }
        }
      },
      entity: {
        // Subject Object
        id: -1,
        code: '',
        name: '',
        timeModify: 0
      },
      validate: {
        // empty
        code: false,
        name: false
      },
      validateFinal: false, // 檢查結果

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
    var v = this.validate
    var e = this.entity
    v.code = (e.code !== null) && (e.code !== '')
    v.name = (e.name !== null) && (e.name !== '')
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
              // Subject Object
              id: bean.id,
              code: bean.code,
              name: bean.name
            }
          } else {
            console.log(`>>> Error: 'bean' is not defined.`)
          }
          break
        // 其餘頁面
        default:
          self.entity = {
            // Subject Object
            id: -1,
            code: '',
            name: ''
          }
          break
      }
      self.validate = {
        // empty
        code: false,
        name: false
      }
      self.validateFinal = false
    },

    /* Bean */

    // 查詢欄位 -> filter 資料用
    queryBean (bean) {
      var apiBean = {
        code: '',
        name: ''
      }
      if (bean) {
        apiBean = {
          code: (bean.code !== '') ? bean.code : '',
          name: (bean.name !== '') ? bean.name : ''
        }
      }
      return apiBean
    },
    // 新增修改資料 -> create, update 資料用
    saveBean (bean) {
      return {
        id: bean.id,
        code: bean.code,
        name: bean.name
      }
    },
    // 打包 API 用 Entity
    pkgApiEntity (apiBean) {
      return {
        subject: apiBean
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
            if (response.data.statusCode === 595) {
              alert('科目編號重複, 請重新編號')
            } else {
              self.queryEntity()
              alert('新增完成')
            }
          }
        }).catch(function (error) {
          console.log('>>> Error: Add ' + self.API.entityName + ' failed: ', error)
        })
      } else {
        alert('請檢查是否有欄位未填寫')
      }
    },
    updateEntity (bean) {
      var self = this
      var v = this.validate
      var apiBean = this.saveBean(bean)

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
      } else {
        alert('請檢查是否有欄位未填寫')
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
            alert('刪除完成')
          }
        }).catch(function (error) {
          console.log('>>> Error: Delete ' + self.API.entityName + ' failed: ', error)
        })
      }
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
