package vn.edu.fpt.fa24.Helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import vn.edu.fpt.fa24.Callbacks.DatePickerCallback;

public class DateHelper {
    private final Context context;
    private String selectedDate;

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public DateHelper(Context context) {
        this.context = context;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public String parseDate(String rawDate) {
        return LocalDateTime.parse(rawDate, formatter).toLocalDate().toString();
    }

    public String showDate(String rawDate) {
        try {
            Log.i("show date", rawDate);
            String dateOnly = rawDate.split("T")[0];
            String[] date = dateOnly.split("-");
            return date[2] + "/" + date[1] + "/" + date[0];
        } catch (Exception e) {
            Log.i("show date error", e.toString());
            return "";
        }
    }

    public String showSelectedDate() {
        try {
            String[] date = selectedDate.split("-");
            return date[1] + "/" + date[2] + "/" + date[0];
        } catch (Exception e) {
            Log.i("show date error", e.toString());
            return "";
        }
    }

    public Boolean isValidPastDate(String rawDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(rawDate, formatter);

        return date.compareTo(LocalDate.now()) <= 0;
    }

    public Boolean isValidFutureDate(String rawDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(rawDate, formatter);

        return date.compareTo(LocalDate.now()) > 0;
    }

    public String formatDate(String rawDate) {
        try {
            Log.i("format date", rawDate);
            String[] date = rawDate.split("/");
            return date[2] + "-" + date[1] + "-" + date[0];
        } catch (Exception e) {
            Log.i("format date error", e.toString());
            return "";
        }
    }

    public void showDatePickerDialog(DatePickerCallback callback) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Initialize DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format selected date and set it to the EditText
                    String validDay = String.valueOf(selectedDay);
                    String validMonth = String.valueOf(selectedMonth + 1);


                    if (validDay.length() == 1) {
                        validDay = "0" + validDay;
                    }

                    if (validMonth.length() == 1) {
                        validMonth = "0" + validMonth;
                    }

                    setSelectedDate(selectedYear + "-" + validDay + "-" + validMonth);
                    callback.setDate(showSelectedDate());
                },
                year, month, day
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}
