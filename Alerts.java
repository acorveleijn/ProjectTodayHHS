package nl.hhs.apep2122group1.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import nl.hhs.apep2122group1.R;
import nl.hhs.apep2122group1.database.FileDatabase;
import nl.hhs.apep2122group1.models.Task;

public class Alerts {

    public static void logoutAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.task_list_logout_btn_title_text)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_confirm_text, (dialog, id) -> ((Activity) context).finish())
                .setNegativeButton(R.string.alert_cancel_text, (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void deleteAlert(Context context, Task task, Runnable onPositiveCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.task_delete_title_text);
        builder.setMessage(R.string.task_delete_message_text)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_confirm_text, (dialog, id) -> {
                    FileDatabase.getDatabase(context).deleteTask(task);
                    onPositiveCallback.run();
                })
                .setNegativeButton(R.string.alert_cancel_text, (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void openDateEditDialog(Activity activity, LocalDateTime currentDateTime, Consumer<LocalDateTime> onDateTimeChanged) {
        // create dialog builder and view
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dateEditDialogView = inflater.inflate(R.layout.dialog_date_edit, null);
        builder.setView(dateEditDialogView);
        Dialog dialog = builder.create();

        // set initial date and picker values
        DatePicker datePicker = dateEditDialogView.findViewById(R.id.date_edit_dialog_dp);
        TimePicker timePicker = dateEditDialogView.findViewById(R.id.date_edit_dialog_tp);
        if (currentDateTime != null) {
            datePicker.updateDate(currentDateTime.getYear(), currentDateTime.getMonthValue() - 1, currentDateTime.getDayOfMonth());
            timePicker.setHour(currentDateTime.getHour());
            timePicker.setMinute(currentDateTime.getMinute());
        }

        // set [cancel, clear, create] button handlers
        Button cancelBtn = dateEditDialogView.findViewById(R.id.date_edit_dialog_cancel_btn);
        cancelBtn.setOnClickListener(cancelBtnView -> dialog.cancel());

        Button clearBtn = dateEditDialogView.findViewById(R.id.date_edit_dialog_clear_btn);
        clearBtn.setOnClickListener(clearBtnView -> {
            onDateTimeChanged.accept(null);
            dialog.dismiss();
        });

        Button createBtn = dateEditDialogView.findViewById(R.id.date_edit_dialog_save_btn);
        createBtn.setOnClickListener(createBtnView -> {
            // extract date and time from DatePicker and TimePicker
            LocalDateTime newDateTime = LocalDateTime.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());

            onDateTimeChanged.accept(newDateTime);
            dialog.dismiss();
        });

        // actually show dialog
        dialog.show();
    }
}
