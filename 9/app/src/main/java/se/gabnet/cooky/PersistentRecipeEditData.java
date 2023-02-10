package se.gabnet.cooky;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import java.util.List;

import se.gabnet.cooky.Database.DatabaseController;

public class PersistentRecipeEditData {

    static DatabaseController databaseController = DatabaseController.getDatabaseController();

    public static List<RecipeEditor> fetchRecipesFromDb() {
        return databaseController.getRecipeEditorList();
    }

    public static void saveToDb(RecipeEditor currentRecipe) {
        databaseController.updateRecipe(currentRecipe);
    }

    public static RecipeEditor getRecipe(RecipeEditor recipeEditor) {
        return databaseController.getRecipeEditor(recipeEditor);
    }

    public static void deleteRecipe(RecipeEditor currentRecipe,Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(DB_UPDATE_NOTIFICATION_CHANNEL,"Db notifier channel",NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(notificationChannel);
        Notification.Builder notificationBuilder = new Notification.Builder(context,DB_UPDATE_NOTIFICATION_CHANNEL);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        notificationBuilder.setContentText("Updating database");
        notificationBuilder.setSubText("Removing the selected recipe " + currentRecipe.getTitle()+ ".");
        Notification notification = notificationBuilder.build();
        notificationManager.notify(1,notification);
        databaseController.deleteRecipe(currentRecipe);
    }


    static final String DB_UPDATE_NOTIFICATION_CHANNEL = "DB_UPDATE_NOTIFICATION_CHANNEL";
    public static long update(RecipeEditor currentRecipe, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(DB_UPDATE_NOTIFICATION_CHANNEL,"Db notifier channel",NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(notificationChannel);
        Notification.Builder notificationBuilder = new Notification.Builder(context,DB_UPDATE_NOTIFICATION_CHANNEL);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        notificationBuilder.setContentText("Updating database");
        notificationBuilder.setSubText("Updating the database with the newly updated/saved recipe " + currentRecipe.getTitle()+ ".");
        Notification notification = notificationBuilder.build();
        notificationManager.notify(1,notification);

        return databaseController.updateRecipe(currentRecipe);
    }
}


