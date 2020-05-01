<template>
  <div style="font-size: 10 px">
    <div style="padding-top: 1%">
      <div>
        <h1>Journal & Cash</h1>
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
                  <th>Date</th>
                  <th>Debit</th>
                  <th>Credit</th>
                  <th>Reduce / Increase</th>
                  <th>Amount</th>
                  <th>Info (Item / Place / Who)</th>
                  <th>Account</th>
                  <th>Time Modify</th>
                  <th>Operate</th>
                </tr>
                <tr>
                  <th></th>
                  <th>
                    <!-- <div class="form-group">
                      <div class="block">
                        <el-date-picker
                          v-model="entity.timeDate"
                          type="datetime"
                          placeholder="請選擇日期及時間"
                          format="yyyy-MM-dd"
                          align="right"
                          style="height:25px"/>
                      </div>
                    </div> -->
                    <input type="date" class="form-control" placeholder="起始" v-model="entity.timeDate" >
                    <input type="date" class="form-control" placeholder="迄止" v-model="entity.timeDateEnd" >
                    <div style="margin-bottom: 10px"></div>
                    <button type="button" class="btn btn-outline-info btn-circle" @click="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)" title="search">
                      <i class="material-icons">search</i>
                    </button>
                    <button type="button" class="btn btn-outline-warning btn-circle" @click="queryEntity()" title="reset">
                      <i class="material-icons">restore</i>
                    </button>
                  </th>
                  <th>
                    <select class="custom-select"
                      v-model="entity.debit" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
                      <option v-for="(option, index) in subjectList" :key="index" :value="option.id">{{ option.name }}</option>
                    </select>
                  </th>
                  <th>
                    <select class="custom-select"
                      v-model="entity.credit" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)">
                      <option v-for="(option, index) in subjectList" :key="index" :value="option.id">{{ option.name }}</option>
                    </select>
                  </th>
                  <th></th>
                  <th></th>
                  <th>
                    <input type="text" class="form-control" placeholder="請輸入項目..."
                      v-model="entity.item" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)" >
                    <input type="text" class="form-control" placeholder="請輸入地點..."
                      v-model="entity.place" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)" >
                    <input type="text" class="form-control" placeholder="誰..."
                      v-model="entity.who" @change="queryEntity(entity, PARAMS.Layout.Action.Filter.symbol)" >
                  </th>
                  <th></th>
                  <th></th>
                  <th>
                    <button type="button" class="btn btn-primary" @click="toLayout(PARAMS.Layout.Add.symbol)">Add</button>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(bean, index) in entityList" :key="index">
                  <td>{{ bean.id }}</td>
                  <td>{{ toFormatDateTime(bean.timeDate, 'YYYY-MM-DD') }}</td>
                  <td>{{ getSubjectText(bean.debit) }}</td>
                  <td>{{ getSubjectText(bean.credit) }}</td>
                  <td align="center">{{ ((bean.reduce === undefined) && (bean.increase === undefined)) ? '' : ((bean.increase > 0) ? '+' : '-') }}</td>
                  <td>{{ bean.amount }}</td>
                  <td>{{ (bean.item.length > 10) ? bean.item.substring(0, 10) + '...' : bean.item }}<br/>{{ bean.place }}<br />{{ bean.who }}</td>
                  <td>{{ getAccountText(bean.accountId) }}</td>
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

            <!-- timeDate: datetimepicker -->
            <div v-if="thisLayout === PARAMS.Layout.Add.value">
              <label for="timeDate">Date</label>
              <input type="date" class="form-control is-invalid" placeholder="timeDate" required
                v-model="entity.timeDate" id="timeDate">
              <div v-if="!validate.timeDate" class="invalid-feedback">不可空白</div>
            </div>
            <div v-if="thisLayout === PARAMS.Layout.Edit.value">
              <label>Date</label>
              <input type="text" class="form-control" placeholder="timeDate" readonly
                :value="toFormatDateTime(entity.timeDate, 'YYYY-MM-DD')">
            </div>
            <br />

            <div class="container">
              <div class="row">
                <div class="col-sm">
                  <!-- debit -->
                  <div>
                    <label for="debit">Debit</label>
                    <div class="form-group">
                      <select class="custom-select" id="debit" v-model="entity.debit" required>
                        <option v-for="(subject, index) in subjectList" :key="index" :value="subject.id">{{ subject.name }}</option>
                      </select>
                      <div v-if="!validate.debit" class="invalid-feedback">請選擇借項科目</div>
                    </div>
                  </div>
                </div>
                <div class="col-sm">
                  <!-- credit -->
                  <div>
                    <label for="credit">Credit</label>
                    <div class="form-group">
                      <select class="custom-select" id="credit" v-model="entity.credit" required>
                        <option v-for="(subject, index) in subjectList" :key="index" :value="subject.id">{{ subject.name }}</option>
                      </select>
                      <div v-if="!validate.credit" class="invalid-feedback">請選擇貸項科目</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Amount -->
            <div v-if="(entity.debit !== cashId) && (entity.credit !== cashId)">
              <label for="amount">Amount</label>
              <input v-model="entity.amount" type="number" class="form-control is-invalid" id="amount" placeholder="Amount" required>
              <div v-if="!validate.amount" class="invalid-feedback">不可空白</div>
            </div>

            <div class="container" v-else>
              <div class="row">
                <!-- Increase -->
                <div class="col-sm" v-if="(entity.debit === cashId) && (entity.credit === cashId)">
                  <div>
                    <label for="increase">Increase</label>
                    <input v-model="entity2.increase" type="number" class="form-control is-invalid" id="increase" placeholder="Increase" required>
                    <div v-if="!validate2.increase" class="invalid-feedback">不可空白</div>
                  </div>
                </div>
                <!-- Reduce -->
                <div class="col-sm" v-if="(entity.debit === cashId) && (entity.credit === cashId)">
                  <div>
                    <label for="reduce">Reduce</label>
                    <input v-model="entity2.reduce" type="number" class="form-control is-invalid" id="reduce" placeholder="Reduce" required>
                    <div v-if="!validate2.reduce" class="invalid-feedback">不可空白</div>
                  </div>
                </div>
                <!-- Amount -->
                <div class="col-sm">
                  <div>
                    <label for="amount">Amount</label>
                    <input v-model="entity.amount" type="number" class="form-control is-invalid" id="amount" placeholder="Amount" required>
                    <div v-if="!validate.amount" class="invalid-feedback">不可空白</div>
                  </div>
                </div>
              </div>
            </div>
            <br />

            <!-- Item -->
            <div>
              <label for="item">Item</label>
              <input v-model="entity.item" type="text" class="form-control is-invalid" id="item" placeholder="Item" required>
              <div v-if="!validate.item" class="invalid-feedback">不可空白</div>
            </div>
            <br />

            <!-- Place -->
            <div>
              <label for="place">Place</label>
              <input v-model="entity.place" type="text" class="form-control" id="-" placeholder="Place">
            </div>
            <br />

            <!-- Who -->
            <div>
              <label for="who">Who</label>
              <input v-model="entity.who" type="text" class="form-control" id="-" placeholder="Who">
            </div>
            <br />

            <!-- Account -->
            <div>
              <label for="accountId">Account</label>
              <div class="form-group">
                <select class="custom-select" id="accountId" v-model="entity.accountId" required>
                  <option v-for="(account, index) in accountList" :key="index" :value="account.id">{{ account.account }}</option>
                </select>
                <div v-if="!validate.accountId" class="invalid-feedback">請選擇帳號</div>
              </div>
            </div>

          </form>
          <button type="button" class="btn btn-outline-dark" @click="toLayout(PARAMS.Layout.Action.Cancel.symbol)">Cancel</button>
          <button v-if="thisLayout === PARAMS.Layout.Add.value"
                  type="button" class="btn btn-outline-primary" @click="createEntity(entity, entity2)">Finish</button>
          <button v-if="thisLayout === PARAMS.Layout.Edit.value"
                  type="button" class="btn btn-outline-primary" @click="updateEntity(entity, entity2)">Update</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import loading from './Loading.vue'

export default {
  name: 'Journal',
  components: {
    'loading': loading
  },
  data () {
    return {
      API: {
        entityName: 'journal',
        query: {
          method: 'post',
          url: '/onAccountX/srv/journal'
        },
        create: {
          method: 'put',
          url: '/onAccountX/srv/journal'
        },
        update: {
          method: 'put',
          url: '/onAccountX/srv/journal/' // +id
        },
        delete: {
          method: 'delete',
          url: '/onAccountX/srv/journal/' // +id
        },
        querySubjectName: {
          method: 'get',
          url: '/onAccountX/srv/subject/list/name' // only id & code & name
        },
        queryAccountName: {
          method: 'get',
          url: '/onAccountX/srv/account/list/account' // only id & account
        }
        // cashAccount: {
        //   create: {
        //     method: 'put',
        //     url: '/onAccountX/srv/cashaccount'
        //   },
        //   update: {
        //     method: 'put',
        //     url: '/onAccountX/srv/cashaccount/' // +id
        //   }
        // }
      },
      PARAMS: {
        Layout: {
          Manage: { symbol: 'manage', value: 0, text: '日記帳/現金帳維護' },
          Add: { symbol: 'add', value: 1, text: '日記帳/現金帳新增' },
          Edit: { symbol: 'edit', value: 2, text: '日記帳/現金帳編輯' },
          Action: {
            Filter: { symbol: 'filter' },
            Cancel: { symbol: 'cancel' }
          }
        }
      },
      entity: {
        // Journal Object
        id: -1,
        timeDate: null,
        debit: null, // ui 顯示 "請選擇..."
        credit: null,
        amount: -1,
        item: '',
        place: '',
        who: '',
        accountId: null,
        timeModify: -1,
        // other
        timeDateEnd: null
      },
      entity2: {
        // CashAccount Object
        increase: 0,
        reduce: 0
      },

      // validate option
      validate: {
        // empty
        timeDate: false,
        debit: false,
        credit: false,
        amount: false,
        item: false,
        // place: false, // 不驗證
        // who: false, // 不驗證
        accountId: false
      },
      validate2: {
        // empty
        increase: false,
        reduce: false
      },
      validateFinal: false, // 檢查結果

      entityList: [],
      accountList: [],
      subjectList: [{
        id: null, // ui 顯示 "請選擇..."
        code: null,
        name: '請選擇...'
      }],
      cashId: -1, // 判斷是否要填現金簿資訊
      cashCode: '1-1-1-1',

      // layout
      thisLayout: 0,

      // loading
      display: true,
      loadingCode: 0
    }
  },
  mounted () {
    this.queryEntity()
    this.querySubjectName()
    this.queryAccountName()
  },
  updated () {
    var v = this.validate
    var e = this.entity
    v.timeDate = e.timeDate !== null
    v.debit = e.debit !== null
    v.credit = e.credit !== null
    v.amount = (e.amount !== null) && (e.amount !== -1)
    v.item = (e.item !== null) && (e.item !== '')
    // place 不驗證
    // who 不驗證
    v.accountId = e.accountId !== null
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
              // Journal Object
              id: bean.id,
              timeDate: bean.timeDate,
              debit: bean.debit,
              credit: bean.credit,
              amount: bean.amount,
              item: bean.item,
              place: bean.palce,
              who: bean.who,
              accountId: bean.accountId,
              timeModify: -1, // 由 API 處理,
              // other
              timeDateEnd: null
            }
            self.entity2 = {
              increase: (bean.credit === self.cashId) ? 0 : bean.amount,
              reduce: (bean.debit === self.cashId) ? 0 : bean.amount
            }
          } else {
            console.log(`>>> Error: 'bean' is not defined.`)
          }
          break
        // 其餘頁面
        default:
          self.entity = {
            // Journal Object
            id: -1,
            timeDate: null,
            debit: null,
            credit: null,
            amount: null, // 不使用 -1
            item: '',
            place: '',
            who: '',
            accountId: null,
            timeModify: -1, // 不處理,
            // other
            timeDateEnd: null
          }
          self.entity2 = {
            increase: 0,
            reduce: 0
          }
          break
      }
      self.validate = {
        // empty
        timeDate: false,
        debit: false,
        credit: false,
        amount: false,
        item: false,
        accountId: false
      }
      self.validate2 = {
        increase: false,
        reduce: false
      }
      self.validateFinal = false
    },

    /* Bean */

    // 查詢欄位 -> filter 資料用
    queryBean (bean) {
      var self = this
      var apiBean = {
        timeDate: null,
        debit: null,
        credit: null,
        item: '',
        place: '',
        who: '',
        // other
        timeDateEnd: null
      }
      if (bean) {
        var obj = self.checkTime(bean.timeDate, bean.timeDateEnd)
        var start = obj.start
        var ended = obj.ended
        apiBean.timeDate = (start !== null) ? start : null
        apiBean.debit = (bean.debit !== null) ? bean.debit : null
        apiBean.credit = (bean.credit !== null) ? bean.credit : null
        apiBean.item = (bean.item !== '') ? bean.item : null
        apiBean.place = (bean.place !== '') ? bean.place : null
        apiBean.who = (bean.who !== '') ? bean.who : null
        // other
        apiBean.timeDateEnd = (ended !== null) ? ended : null
      }
      return apiBean
    },
    // 新增修改資料 -> create, update 資料用
    saveBean (bean, bean2) {
      return {
        id: bean.id,
        // bean,timeDate is String (yyyy-MM-dd)
        timeDate: (bean.timeDate === null) ? null : new Date(bean.timeDate).getTime(),
        debit: bean.debit,
        credit: bean.credit,
        amount: bean.amount,
        item: bean.item,
        place: bean.place,
        who: bean.who,
        accountId: bean.accountId,
        timeModify: bean.timeModify,
        // CashAccount
        increase: bean2.increase,
        reduce: bean2.reduce
      }
    },
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
    createEntity (bean, bean2) {
      var self = this
      var v = this.validate
      var v2 = this.validate2

      var obj = self.reflashIncreaseAndReduce()
      bean2.increase = obj.increase
      bean2.reduce = obj.reduce

      var apiBean = self.saveBean(bean, bean2)

      // all check
      var final = true
      for (var item in v) {
        if (!v[item]) {
          final = false
        }
      }
      for (var item2 in v2) {
        if (!v2[item2]) {
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
            if (response.data.statusCode !== 200) {
              alert('新增失敗')
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
    updateEntity (bean, bean2) {
      var self = this
      var v = this.validate
      var v2 = this.validate2

      var obj = self.reflashIncreaseAndReduce()
      bean2.increase = obj.increase
      bean2.reduce = obj.reduce

      var apiBean = this.saveBean(bean, bean2)

      // all check
      var final = true
      for (var item in v) {
        if (!v[item]) {
          final = false
        }
        // console.log(item + ':' + v[item])
      }
      for (var item2 in v2) {
        if (!v2[item2]) {
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
            if (response.data.statusCode !== 200) {
              alert('更新失敗')
            } else {
              self.queryEntity()
              alert('更新完成')
            }
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
      if (confirm('確定要刪除 id ' + id + ' ?')) {
        axios({
          method: self.API.delete.method,
          url: self.API.delete.url + id,
          headers: {
            'Content-Type': 'application/json',
            'mac': 'helloJWT'
          }
        }).then(function (response) {
          if (response) {
            if (response.data.statusCode !== 200) {
              alert('刪除失敗')
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

    // 取得 subject list -> only id & code & name
    querySubjectName () {
      var self = this
      axios({
        method: this.API.querySubjectName.method,
        url: this.API.querySubjectName.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {}
      }).then(function (response) {
        if (response) {
          self.subjectList = self.subjectList.concat(response.data.data)
        }
        self.subjectList.forEach(subject => {
          if (subject.code === self.cashCode) { // 現金編號 1-1-1-1
            self.cashId = subject.id
          }
        })
        // console.log('cashId: ', self.cashId)
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query ' + self.API.entityName + ' failed: ', error)
      })
    },
    // 取得 account list -> only id & account
    queryAccountName () {
      var self = this
      axios({
        method: self.API.queryAccountName.method,
        url: self.API.queryAccountName.url,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {}
      }).then(function (response) {
        if (response) {
          self.accountList = response.data.data
        }
      }).catch(function (error) {
        console.log('>>> Error: validate ' + self.API.entityName + ' failed: ', error)
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
    // 取得 subject text by id
    getSubjectText (id) {
      var self = this
      var str = 'Error'
      self.subjectList.forEach(s => {
        if (id === s.id) {
          str = s.name
        }
      })
      return str
    },
    // 取得 account text by id
    getAccountText (id) {
      var self = this
      var str = 'Error'
      self.accountList.forEach(a => {
        if (id === a.id) {
          str = a.account
        }
      })
      return str
    },
    // 自動設定 increase & reduce
    reflashIncreaseAndReduce () {
      var self = this
      var v2 = this.validate2
      var e = this.entity
      var increase = null
      var reduce = null

      var b1 = e.debit === self.cashId
      var b2 = e.credit === self.cashId
      if (b1 && b2) {
        // 不直接設定
      }
      if (!b1 && !b2) {
        increase = 0
        reduce = 0
      }
      if (b1 && !b2) {
        increase = e.amount
        reduce = 0
      }
      if (!b1 && b2) {
        increase = 0
        reduce = e.amount
      }
      v2.increase = increase !== null
      v2.reduce = reduce !== null
      return { 'increase': increase, 'reduce': reduce }
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
