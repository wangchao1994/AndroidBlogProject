package com.raisesail.base_ui;

import android.content.Context;

public interface IBaseView {
    void bindView();
    void unBindView();
    Context getViewContext();
}
