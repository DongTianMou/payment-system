package com.dongtian.paymentsystem.utils;


import com.dongtian.paymentsystem.vo.ResultVO;

public class ResultVOUtils {

    public static ResultVO success(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg( "success" );
        resultVO.setCode(0);
        resultVO.setData( o );
        return resultVO;
    }

    public static ResultVO success(){
        return success( null );
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }


}
