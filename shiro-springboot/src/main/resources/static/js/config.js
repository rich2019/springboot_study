const config = {
    // 是否调试
    debug: true
};

// 设置全局路径
axios.defaults.baseURL = "/";

/**
 * 发送请求
 * @author 张晓琳
 * @date 2019-09-25 02:19
 * @param option 参考axios文档配置说明
 * @return
 * @version 1.0
 */
function request(option) {
    let headers = option.headers || {};
    // headers['userid'] = config.userInfo.userid;
    let opt = {
        headers: headers,
        url: option.url,
        params: option.data,
        method: option.method || 'POST',
        dataType: option.dataType || 'json',
        success(res) {
            if (res.status === 200) {
                option.success(res.data)
            } else {
                console.error("请求发生错误", res.status, url, res)
            }
        },
        fail: option.fail || function (res) {
            console.error("发生发生异常:", res);
        },
        complete: option.complete
    };
    axios.request(opt).then((res) = > {
        if(option.complete
)
    {
        option.complete();
    }
    if (res.status === 200) {
        if (option.success) {
            option.success(res.data);
        }
    } else {
        console.error("请求发生错误", res);
    }
}).
    catch((err) = > {
        if(option.complete
)
    {
        option.complete();
    }
    console.error("请求发生异常", err);
})
    ;
}
