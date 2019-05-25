package com.example.m.firstapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TaskViewHolder> {

    private List<ToDoTask> tasks;
    private Context context;
    private View view;


    public ToDoListAdapter(List<ToDoTask> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }


    @Override
    public ToDoListAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ToDoListAdapter.TaskViewHolder holder, final int position) {
        ToDoTask task = tasks.get(position);
        holder.taskNameView.setText(task.getTitle());
        holder.taskDescView.setText("Date: " + new SimpleDateFormat("dd.MM.yyyy").format(task.getDate()));


            Date currentTime = Calendar.getInstance().getTime();
            if(currentTime.after(task.getDate())){
                holder.taskCardView.setBackgroundColor(Color.rgb(255,180,180));
            }
            else {
                holder.taskCardView.setBackgroundColor(Color.parseColor("#FCFCFC"));
            }


        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                popup.inflate(R.menu.options_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuEditOpt:
                                editTask(position);
                                break;
                            case R.id.menuDeleteOpt:
                                tasks.remove(position);
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }


    public void editTask(int position) {
        ((MainActivity) context).openEditTaskActivity(2, position);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskNameView;
        public TextView taskDescView;
        public TextView textViewOptions;
        public CardView taskCardView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskNameView = itemView.findViewById(R.id.listTaskName);
            taskDescView = itemView.findViewById(R.id.listTaskDesc);
            textViewOptions = itemView.findViewById(R.id.textViewOptions);
            taskCardView = itemView.findViewById(R.id.taskCardView);
        }
    }
}

//    private Context context;
//    private List<String> taskNames = new ArrayList<>();
//    private List<String> taskDescriptions = new ArrayList<>();
//    private View view;
//
//    public ToDoListAdapter(Context context, List<String> taskNames, List<String> taskDescriptions) {
//        this.context = context;
//        this.taskNames = Arrays.asList("Zad1","Zad2","Zad3", "Zad 4", "", "Zad dddddddddddddddddddddddddddddddddddddddddddddddddd5", "sadas", "sadsad", "sadas");
//        this.taskDescriptions = Arrays.asList("opis1", "sa", "aaaaaaaaaasasdsad   DDDDDDDDDDDDDDDDDaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaadasdas","opis\n3 xD", "as", "", "dfs", "asasasa", "sdas");
//    }
//
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
//        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
//        return taskViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
//        String taskName = taskNames.get(position);
//        String taskDesc = taskDescriptions.get(position);
//        holder.taskNameView.setText(taskName);
//        holder.taskDescView.setText(taskDesc);
//
//        //if(position%2==0) {
//            view.setBackgroundColor(Color.parseColor("#FCFCFC"));
//            //Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
////        } else {
////            view.setBackgroundColor(Color.parseColor("#EFEFEF"));
////            //Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
////        }
//
////        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption);
////                //inflating menu from xml resource
////                popup.inflate(R.menu.options_menu);
////                //adding click listener
////                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
////                    @Override
////                    public boolean onMenuItemClick(MenuItem item) {
////                        switch (item.getItemId()) {
////                            case R.id.menu1:
////                                //handle menu1 click
////                                return true;
////                            case R.id.menu2:
////                                //handle menu2 click
////                                return true;
////                            case R.id.menu3:
////                                //handle menu3 click
////                                return true;
////                            default:
////                                return false;
////                        }
////                    }
////                });
////                //displaying the popup
////                popup.show();
////
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return taskNames.size();
//    }
//
//    public static class TaskViewHolder extends RecyclerView.ViewHolder {
//        public TextView taskNameView;
//        public TextView taskDescView;
//        public TextView textViewOptions;
//        //public RelativeLayout listItemLayout;
//
//        public TaskViewHolder(View itemView) {
//            super(itemView);
//            taskNameView = itemView.findViewById(R.id.listTaskName);
//            taskDescView = itemView.findViewById(R.id.listTaskDesc);
//            textViewOptions = itemView.findViewById(R.id.textViewOptions);
//            //listItemLayout = itemView.findViewById(R.id.listItemLayout);
//        }
//    }
//
//
//}
