package com.wolfie.kidspend2.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wolfie.kidspend2.model.database.Helper;
import com.wolfie.kidspend2.model.database.Source;
import com.wolfie.kidspend2.model.loader.IoLoader;
import com.wolfie.kidspend2.model.loader.SpendLoader;
import com.wolfie.kidspend2.view.BaseUi;

public class MainPresenter extends BasePresenter<BaseUi> {

    private Helper mHelper;
    private SQLiteDatabase mDatabase;
    private Source mSource;
    private IoLoader mIoLoader;
    private SpendLoader mSpendLoader;

    public MainPresenter(BaseUi baseUi, Context context) {
        super(baseUi);

        mHelper = new Helper(context);
        mDatabase = mHelper.getWritableDatabase();
        mSource = new Source(mDatabase);
        mIoLoader = new IoLoader(mSource);
        mSpendLoader = new SpendLoader(mSource);
    }

    public IoLoader getIoLoader() {
        return mIoLoader;
    }

    public SpendLoader getSpendLoader() {
        return mSpendLoader;
    }

}
