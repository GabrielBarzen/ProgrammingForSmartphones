package se.gabnet.cooky;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.List;
import java.util.Random;

import se.gabnet.cooky.Database.DatabaseController;

/**
 * Implementation of App Widget functionality.
 */
public class RandomRecipe extends AppWidgetProvider {


    static void update(Context context){
        Intent updateWidget = new Intent();
        updateWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,RandomRecipe.class));
        updateWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(updateWidget);
    }

    private static RecipeEditor editor;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        System.out.println("UPDATING WIDGET");
        List<RecipeEditor> recipeEditorList = DatabaseController.getDatabaseController().getRecipeEditorList();
        if (recipeEditorList.size() > 0) {
            System.out.println("Getting rand recipe");
            Random random = new Random();

            editor = recipeEditorList.get(random.nextInt(recipeEditorList.size()));
            CharSequence widgetText = editor.getTitle();
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_recipe);
            views.setTextViewText(R.id.appwidget_text, widgetText);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } else {
            CharSequence widgetText = "No recipes available";
            System.out.println("Setting missing text");
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_recipe);
            views.setTextViewText(R.id.appwidget_text, widgetText);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}