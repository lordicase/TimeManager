package com.example.timemanager;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProjectRepository {
    private ProjectDao projectDao;
    private LiveData<List<Project>> allProject;

    ProjectRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        projectDao = dataBase.projectDao();
        allProject = projectDao.getAllProjects();
    }

    public void insert(Project project) {
        new InsertNoteAsyncTask(projectDao).execute(project);
    }

    public void update(Project project) {
        new UpdateAsyncTask(projectDao).execute(project);
    }

    public void delete(Project project) {
        new DeleteProjectAsyncTask(projectDao).execute(project);
    }

    public LiveData<List<Project>> getAllProject() {
        return allProject;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private InsertNoteAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.insert(projects[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private UpdateAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.update(projects[0]);
            return null;
        }
    }

    private static class DeleteProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private DeleteProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.delete(projects[0]);
            return null;
        }
    }
}
