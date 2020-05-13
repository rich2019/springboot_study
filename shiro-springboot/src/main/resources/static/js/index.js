var global = {
    // 加载窗
    loading: {},
    // 内容
    mainBox: {}
};

global.loading = new Vue({
    el: "#loading-box",
    data: {
        isShow: false
    },
    methods: {

        show: function () {
            this.isShow = true;
        },

        hide: function () {
            this.isShow = false;
        }

    }
});

/**
 *
 * @author 张晓琳
 * @date 2020-04-27 18:33
 * @version 1.0
 */
global.mainBox = new Vue({
    el: "#main-box",
    data: {
        /**
         * 服务器列表
         * @author 张晓琳
         * @date 2020-04-27 18:34
         * @version 1.0
         */
        serverList: [],

        /**
         * 服务器过滤关键字
         * @author 张晓琳
         * @date 2020-05-08 19:31
         * @version 1.0
         */
        serverFilterKeyword: '',

        /**
         * 待执行的命令
         * @author 张晓琳
         * @date 2020-05-08 17:54
         * @param null
         * @version 1.0
         */
        cmd: '',

        /**
         * 当前已选择的服务器
         * @author 张晓琳
         * @date 2020-05-08 19:25
         * @version 1.0
         */
        curtSelectedServerIp: null,

        /**
         * 是否显示某一台服务器详情
         * @author 张晓琳
         * @date 2020-04-27 18:35
         * @version 1.0
         */
        isShowServers: true,

        /**
         * 是否check所有
         * @author 张晓琳
         * @date 2020-05-08 18:42
         * @version 1.0
         */
        isCheckedAll: false,

        /**
         * 是否显示加载中
         * @author 张晓琳
         * @date 2020-05-08 19:17
         * @version 1.0
         */
        isShowLoading: false


    },
    mounted: function () {
        // 定时刷新服务器集群列表
        this._refreshServerList();
        setInterval(this._refreshServerList, 5000);
    },
    methods: {

        /**
         * 显示某个服务器上的服务（选中一个服务器时）
         * @author 张晓琳
         * @date 2020-04-27 18:58
         * @version 1.0
         */
        showServerDetail: function (ip) {
            console.debug("开始请求服务器信息");
            // 显示服务列表
            this.isShowServers = true;
            let serverIp = ip == null ? this._getSelectedServer() : ip;
            // 判断当前服务器的服务列表是否打开过
            if (this.curtSelectedServerIp === serverIp) {
                return;
            }
            this.curtSelectedServerIp = serverIp;
            // 请求该服务器上的服务信息
            global.loading.show();
            request({
                url: 'monitor/services',
                method: 'GET',
                data: {
                    ip: serverIp
                },
                success: function (res) {
                    console.debug("该服务器运行服务信息", res);
                    if (res.status === "success") {

                    }
                },
                complete: function () {
                    global.loading.hide();
                }
            });
        },

        /**
         * 显示集群控制台
         * @author 张晓琳
         * @date 2020-04-27 18:59
         * @version 1.0
         */
        showClusterConsole: function () {
            console.debug("显示集群控制台");
            this.isShowServers = false;
        },

        /**
         * 选中某个服务器
         * @author 张晓琳
         * @date 2020-04-27 18:55
         * @version 1.0
         */
        selectServer: function (index) {
            console.log("选中当前服务器，索引", index);
            // 若没有服务列表中没有checked的服务则执行选中逻辑，否则忽略选中事件
            let checkedServers = this._getCheckedServers();
            if (checkedServers.length === 0) {
                // 先把所有都取消选中
                for (let i in this.serverList) {
                    let server = this.serverList[i];
                    server.isSelected = false;
                }
                // 显示当前服务器上的服务详情
                let server = this.serverList[index];
                server.isSelected = true;
                // 显示该服务器上的所有服务
                this.showServerDetail(server.ip);
            }
        },

        /**
         * 选中一台服务器的checkbox（若至少有一台server被check则进入控制台命令模式）
         * @author 张晓琳
         * @date 2020-05-02 12:22
         * @param null
         * @version 1.0
         */
        checkedServer: function (index) {
            console.log("check当前服务器，索引", index);
            let server = this.serverList[index];
            server.isChecked = !server.isChecked;
            // 若有一台被选中则打开命令控制台
            let isShowClusterConsole = false;
            for (let i in this.serverList) {
                let server = this.serverList[i];
                if (server.isChecked) {
                    isShowClusterConsole = true;
                    break;
                }
            }
            if (isShowClusterConsole) {
                console.log("进入集群控制模式");
                this.showClusterConsole();
            } else {
                this.isCheckedAll = false;
                console.log("进入服务查看模式");
                this.showServerDetail(null);
            }
        },

        /**
         * 过滤服务器列表
         * @author 张晓琳
         * @date 2020-05-08 19:31
         * @version 1.0
         */
        filterServerList: function () {
            let keyword = this.serverFilterKeyword;
            if (keyword !== '') {
                for (let index in this.serverList) {
                    let server = this.serverList[index];
                    if (server.ip.indexOf(keyword) > -1 || server.description.indexOf(keyword) > -1) {
                        server.isShow = true;
                    } else {
                        server.isShow = false;
                    }
                }
            } else {
                for (let index in this.serverList) {
                    let server = this.serverList[index];
                    server.isShow = true;
                }
            }
        },

        /**
         * 执行命令
         * @author 张晓琳
         * @date 2020-05-08 17:46
         * @param null
         * @version 1.0
         */
        executeCmd: function () {
            for (var index in this.serverList) {
                let server = this.serverList[index];
                if (server.isChecked) {
                    console.log("服务器" + server.ip + "执行命令：" + this.cmd);
                }
            }
        },

        /**
         * 执行
         * @author 张晓琳
         * @date 2020-05-08 20:23
         * @version 1.0
         */
        _exec: function (cmd, callback) {

        },

        /**
         * 所有服务器的checkbox都被选中/反选
         * @author 张晓琳
         * @date 2020-05-01 14:20
         * @param null
         * @return
         * @version 1.0
         */
        checkAll: function () {
            let isAllChecked = true;
            for (let i in this.serverList) {
                let server = this.serverList[i];
                if (!server.isChecked) {
                    isAllChecked = false;
                    break;
                }
            }
            // 若所有都被check了则本次点击取消所有check，否则把没check的也check上
            for (let i in this.serverList) {
                let server = this.serverList[i];
                if (isAllChecked) {
                    server.isChecked = false;
                } else {
                    server.isChecked = true;
                }
            }
            if (!isAllChecked) {
                this.showClusterConsole();
            } else {
                // 展示当前selected的服务器上的服务详情
                this.showServerDetail(null);
            }
        },

        /**
         * 获取已选中的服务器(同一时刻只能有一个选中的)
         * @author 张晓琳
         * @date 2020-04-27 18:56
         * @version 1.0
         */
        _getSelectedServer: function () {
            for (let i in this.serverList) {
                let server = this.serverList[i];
                if (server.isSelected) {
                    return server;
                }
            }
            return null;
        },

        /**
         * 获取所有checked的服务器
         * @author 张晓琳
         * @date 2020-05-06 19:07
         * @version 1.0
         */
        _getCheckedServers: function () {
            let servers = [];
            for (let i in this.serverList) {
                let server = this.serverList[i];
                if (server.isChecked) {
                    servers.push(server);
                }
            }
            return servers;
        },

        /**
         * 初始化服务器列表
         * @author 张晓琳
         * @date 2020-04-27 19:29
         * @version 1.0
         */
        _refreshServerList: function () {
            let that = this;
            // 请求集群服务器列表
            request({
                url: 'monitor/servers',
                method: 'GET',
                success: function (res) {
                    console.debug("请求到的集群服务器列表", res);
                    if (res.status === "success") {
                        let newServers = res.data;
                        // ip -> index(newServers中的索引位置)
                        let newServersMap = {};
                        for (let i in newServers) {
                            let server = newServers[i];
                            newServersMap[server.ip] = i;
                        }
                        // 将当前在展示的服务器列表中无效的节点剔除
                        for (let i in that.serverList) {
                            let server = that.serverList[i];
                            if (!newServersMap[server.ip]) {
                                // 在最新的集群节点中不存在，则将当前移除节点移出服务器列表
                                Vue.delete(that.serverList, i);
                            }
                        }
                        // 添加新的节点
                        let curtServersMap = {};
                        for (let i in that.serverList) {
                            let server = that.serverList[i];
                            curtServersMap[server.ip] = i;
                        }
                        // 将当前服务器列表中不存在的新节点加入列表
                        for (let i in newServers) {
                            let server = newServers[i];
                            if (!curtServersMap[server.ip]) {
                                Vue.set(that.serverList, that.serverList.length, {
                                    ip: server.ip,
                                    description: server.description,
                                    port: server.port,
                                    isSelected: false,
                                    isChecked: false,
                                    isShow: true
                                });
                            }
                        }
                        // 若当前列表中没有选中的，没有则默认选中第一个
                        for (let i in that.serverList) {
                            let server = that.serverList[i];
                            if (server.isSelected) {
                                return;
                            }
                        }
                        that.serverList[0].isSelected = true;
                    }
                },
                complete: function () {

                }
            });
        }
    }

});
