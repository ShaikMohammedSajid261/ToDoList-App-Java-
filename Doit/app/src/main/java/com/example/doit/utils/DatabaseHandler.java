package com.example.doit.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.example.doit.Model.ToDoModel;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERRSION =1;
    private static final String NAME="toDoListDatabase";
    private static final String TODO_TABLE="todo";
    private static final String ID="id";
    private static final String TASK="task";
    private static final String STATUS="status";
    private static final String CREATE_TODO_TABLE="CREATE TABLE "+TODO_TABLE+"("+ID +"INTEGER PRIMARY KEY AUTOINCREMENT, "+TASK+"TEXT,"+STATUS+"INTEGER)";

    private SQLiteDatabase db;
    public DatabaseHandler(Context context){
        super(context, NAME,null, VERRSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion, int newVersion){
        //Drop the older tables
        db.execSQL("DROP TABLE IF EXISTS" + TODO_TABLE);
        //Create table again
        onCreate(db);
    }

    public void openDatabase(){
        db= this.getWritableDatabase();
    }
    public void insertTask(ToDoModel task){
        ContentValues cv=new ContentValues();
        cv.put(TASK,task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null,cv);

    }

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel>taskList=new ArrayList<>();
        Cursor cur=null;
        db.beginTransaction();
        try{
            cur=db.query(TODO_TABLE, null,null,null,null,null,null,null);
            if(cur!=null){
                if (cur.moveToFirst()){
                    do{
                        ToDoModel task= new ToDoModel();
                        task.setTask(cur.getString(cur..getColumnIndex(TASK));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }while (cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }
    public void updateStatus(int id,int status){
        ContentValues cv=new ContentValues();
        cv.put(STATUS,status);
        db.update(TODO_TABLE,cv, ID+"=?",new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id,String status) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }
    public void deleteTasks(int id) {
        db.delete(TODO_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }
}

