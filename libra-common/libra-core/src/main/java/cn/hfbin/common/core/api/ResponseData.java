/*
 *    Copyright [2021] [LibraPlatform of copyright huangfubin]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.hfbin.common.core.api;

import lombok.Data;

/**
 * @Description 响应结果
 * @anthor huangfubin
 * @date 2020/9/19 11:56
 */
@Data
public class ResponseData<T>{
    /**
     * 响应码(默认200正常响应)
     */
    private int code = 200;
    /**
     * 响应信息(默认OK)
     */
    private String msg = "SUCESS";
    /**
     * 响应结果(默认成功)
     */
    private boolean success = true;
    /**
     * 响应数据
     */
    private T data;

    private ResponseData(){}

    private ResponseData(T data){
        this.data = data;
    }

    /**
     * 失败响应
     * @param serviceCode
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> error(ServiceCode serviceCode){
        ResponseData responseData = new ResponseData();
        responseData.setCode(serviceCode.getCode());
        responseData.setMsg(serviceCode.getMsg());
        responseData.setSuccess(false);
        return responseData;
    }
    /**
     * 失败响应
     * @param code 响应编码
     * @param msg 响应信息
     * @return
     */
    public static <T> ResponseData<T> error(int code, String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        responseData.setMsg(msg);
        responseData.setSuccess(false);
        return responseData;
    }

    /**
     * 成功响应
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> ok(){
        return new ResponseData<>();
    }

    /**
     * 成功响应：带data数据
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> ok(T data){
        return new ResponseData<>(data);
    }

}
