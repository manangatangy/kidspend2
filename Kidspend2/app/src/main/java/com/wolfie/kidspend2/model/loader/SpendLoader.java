package com.wolfie.kidspend2.model.loader;

import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.DataSet;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.model.database.Source;
import com.wolfie.kidspend2.model.loader.AsyncListeningTask.Listener;

import java.util.List;

public class SpendLoader {

    private Source mSource;

    public SpendLoader(Source source) {
        mSource = source;
    }

    public void read(AsyncListeningTask.Listener<DataSet> listener, Girl girl) {
        new ReadTask(listener).execute(girl);
    }

    public void insert(Spend spend, @Nullable Listener<Boolean> listener) {
        new InsertTask(listener).execute(spend);
    }

    public void update(Spend spend, @Nullable Listener<Boolean> listener) {
        new UpdateTask(listener).execute(spend);
    }

    public void delete(Spend spend, @Nullable Listener<Boolean> listener) {
        new DeleteTask(listener).execute(spend);
    }

    private class ReadTask extends AsyncListeningTask<Girl, DataSet> {
        public ReadTask(@Nullable Listener<DataSet> listener) {
            super(listener);
        }
        @Override
        public DataSet runInBackground(Girl girl) {
            List<Spend> spends = mSource.read(girl);
            DataSet.sort(spends);
            return new DataSet(spends);
        }
    }

    private class InsertTask extends AsyncListeningTask<Spend, Boolean> {
        public InsertTask(@Nullable Listener<Boolean> listener) {
            super(listener);
        }
        @Override
        public Boolean runInBackground(Spend spend) {
            return mSource.insert(spend);
        }
    }

    private class UpdateTask extends AsyncListeningTask<Spend, Boolean> {
        public UpdateTask(@Nullable Listener<Boolean> listener) {
            super(listener);
        }
        @Override
        public Boolean runInBackground(Spend spend) {
            return mSource.update(spend);
        }
    }

    private class DeleteTask extends AsyncListeningTask<Spend, Boolean> {
        public DeleteTask(@Nullable Listener<Boolean> listener) {
            super(listener);
        }
        @Override
        public Boolean runInBackground(Spend spend) {
            return mSource.delete(spend);
        }
    }

}
