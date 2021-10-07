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

package cn.hfbin.common.core.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @Author: huangfubin
 * @Description: DeptCodeUtil 类
 * @Date: 2021/7/7
 */
public class DeptCodeUtil {

    // 数字位数(默认生成3位的数字)
    private static final int numLength = 3;

    /**
     * 根据前一个code，获取同级下一个code
     * 例如:当前最大code为D001A001，下一个code为：D001A002
     *
     * @param code
     * @return
     */
    public static synchronized String genCode(String code) {
        String newcode = "";
        if (StrUtil.isBlank(code)) {
            String zimu = "A";
            String num = getStrNum(1);
            newcode = zimu + num;
        } else {
            String before_code = code.substring(0, code.length() - 1- numLength);
            String after_code = code.substring(code.length() - 1 - numLength);
            char after_code_zimu = after_code.substring(0, 1).charAt(0);
            int after_code_num = Integer.parseInt(after_code.substring(1));

            String nextNum = "";
            char nextZimu = 'A';
            // 先判断数字等于999*，则计数从1重新开始，递增
            if (after_code_num == getMaxNumByLength(numLength)) {
                nextNum = getNextStrNum(0);
            } else {
                nextNum = getNextStrNum(after_code_num);
            }
            // 先判断数字等于999*，则字母从A重新开始,递增
            if(after_code_num == getMaxNumByLength(numLength)) {
                nextZimu = getNextZiMu(after_code_zimu);
            }else{
                nextZimu = after_code_zimu;
            }

            // 例如Z99，下一个code就是Z99A01
            if ('Z' == after_code_zimu && getMaxNumByLength(numLength) == after_code_num) {
                newcode = code + (nextZimu + nextNum);
            } else {
                newcode = before_code + (nextZimu + nextNum);
            }
        }
        return newcode;

    }

    /**
     * 根据父亲code,获取下级的下一个code
     *
     * 例如：父亲CODE:A001
     *       当前CODE:A001B001
     *       获取的code:A001B002
     *
     * @param parentCode   上级code
     * @param localCode    同级code
     * @return
     */
    public static synchronized String genSubCode(String parentCode,String localCode) {
        if(StrUtil.isNotBlank(localCode)){
            return genCode(localCode);
        }else{
            parentCode = parentCode + "A"+ getNextStrNum(0);
        }
        return parentCode;
    }



    /**
     * 将数字前面位数补零
     *
     * @param num
     * @return
     */
    private static String getNextStrNum(int num) {
        return getStrNum(getNextNum(num));
    }

    /**
     * 将数字前面位数补零
     *
     * @param num
     * @return
     */
    private static String getStrNum(int num) {
        return String.format("%0" + numLength + "d", num);
    }

    /**
     * 递增获取下个数字
     *
     * @param num
     * @return
     */
    private static int getNextNum(int num) {
        num++;
        return num;
    }

    /**
     * 递增获取下个字母
     *
     * @param zimu
     * @return
     */
    private static char getNextZiMu(char zimu) {
        if (zimu == 'Z') {
            return 'A';
        }
        zimu++;
        return zimu;
    }

    /**
     * 根据数字位数获取最大值
     * @param length
     * @return
     */
    private static int getMaxNumByLength(int length){
        if(length==0){
            return 0;
        }
        StringBuilder max_num = new StringBuilder();
        for (int i=0;i<length;i++){
            max_num.append("9");
        }
        return Integer.parseInt(max_num.toString());
    }
}
