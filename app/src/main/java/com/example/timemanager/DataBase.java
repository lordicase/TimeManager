package com.example.timemanager;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.timemanager.dao.ProjectDao;
import com.example.timemanager.dao.ProjectSessionDao;
import com.example.timemanager.dao.TaskDao;
import com.example.timemanager.entity.Project;
import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.entity.Task;

@Database(entities = {Project.class, Task.class, ProjectSession.class}, version = 19)
public abstract class DataBase extends RoomDatabase {
    private static DataBase instance;
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();
    public abstract ProjectSessionDao projectSessionDao();

    public static synchronized DataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataBase.class, "Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private final ProjectDao projectDao;

        private PopulateDbAsyncTask(DataBase db){
            projectDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            projectDao.insert(new Project("Work", 28800000, "#E91E63", "MONTUEWEDTHUFRISATSUN"));
            projectDao.insert(new Project("Fun", 3600000, "#CDDC39", "MONTUEWEDTHUFRISATSUN"));
            projectDao.insert(new Project("Learning",7200000 , "#009688", "MONTUEWEDTHUFRISATSUN"));
            return null;
        }
    }
}
