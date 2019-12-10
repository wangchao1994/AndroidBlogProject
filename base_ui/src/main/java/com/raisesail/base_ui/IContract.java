package com.raisesail.base_ui;

/**
 * 统一接口管理
 */
public interface IContract {

    interface IView<T>{
        void showComplete(T data);
        void showFail();
    }

    interface IPresenter{

    }

    interface IModel{
        void adjustData(IModelListener iModelListener,String message);
        interface IModelListener<T>{
            void completed(T t);
            void failed();
        }
    }
}
