package mobileappchallenge.peach;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    public SQLiteOpenHelper openHelper;
    public SQLiteDatabase database;
    public static DatabaseAccess instance;
    public static Context m;


    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    public DatabaseAccess( Context context) {
        this.openHelper = new Connection(context);
        m=context;
    } public static DatabaseAccess test;

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes is also ingredients apparently
     */
    public List<IngrObject> getQuotes() {
        List<IngrObject> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Ingredients.name, SUM(Ingredients.amount) as totalAmount, Ingredients.mUnit\n" +
                "FROM Ingredients\n" +
                "WHERE Ingredients.id IN (SELECT recipeID FROM Schedule\n" +
                "WHERE userID=\"sample_user\"\n" +
                "AND date>=date('now', 'localtime') AND date<date('now', '+14 days'))\n" +
                "GROUP BY name\n" +
                "HAVING totalAmount<>'';", null);
        int s=cursor.getCount();
        String si = String.valueOf(s);
        cursor.moveToFirst();
        int count=0;
        while (!cursor.isAfterLast()) {
            count++;
            if (cursor.isNull(2)) {
                list.add(new IngrObject(cursor.getString(0), cursor.getDouble(1)));
            } else {
                list.add(new IngrObject(cursor.getString(0), cursor.getDouble(1), cursor.getString(2)));
            }

            cursor.moveToNext();
        }
        cursor.close();
        System.out.println(list.size());

        String size = String.valueOf(list.size());
        String counting = String.valueOf(count);
        Log.d(size,"size of list: "+ size+ " cursize: "+si);
        Log.d(counting," number of loops: "+counting);

        return list;

    }
    //----------------------------------------------------------------------------------------------
    public List<SearchObject> getSearchRecipes(String searchString, String nutrient, String ascDesc) {
        /*
        SELECT id, name, calories, carbs
        FROM Recipes
        WHERE (name LIKE '%a%' OR similarTo LIKE '%a%')
        ORDER BY calories ASC;
        */
        Log.d("getSearchRecipes: ", "running");

        String query = "SELECT name, calories, cooktime, id FROM Recipes";
        query += " WHERE (name LIKE '%" + searchString + "%' OR similarTo LIKE '%" + searchString + "%')";

        if (!nutrient.equals("-----")) {
            query += "ORDER BY " + nutrient.toLowerCase();
            if (ascDesc.equals("Ascending")) {
                query += " ASC";
            } else if (ascDesc.equals("Descending")) {
                query += " DESC";
            } else {
                query += " ASC";
            }
        }
        query += ";";

        Log.d("getSearchRecipes: ", query);

        List<SearchObject> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SearchObject(cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));
            Log.d("getSearchRecipes: ", cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<SearchObject> getBookmarkRecipes(String nutrient, String ascDesc) {
        /*
        SELECT id, name, calories, carbs
        FROM Recipes, Bookmarks
        WHERE Recipes.id=Bookmarks.recipeID AND Bookmarks.profileID LIKE 'sample_user'
        ORDER BY calories ASC;
        */
        Log.d("getBookmarkRecipes: ", "running");

        String query = "SELECT name, calories, cooktime, id FROM Recipes, Bookmarks";
        query += " WHERE Recipes.id=Bookmarks.recipeID AND Bookmarks.profileID LIKE 'sample_user' ";

        if (!nutrient.equals("-----")) {
            query += "ORDER BY " + nutrient.toLowerCase();
            if (ascDesc.equals("Ascending")) {
                query += " ASC";
            } else if (ascDesc.equals("Descending")) {
                query += " DESC";
            } else {
                query += " ASC";
            }
        }
        query += ";";

        Log.d("getBookmarkRecipes: ", query);

        List<SearchObject> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Log.d("getBookmarkRecipes: ", cursor.getString(0));
        while (!cursor.isAfterLast()) {
            list.add(new SearchObject(cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));
            Log.d("getBookmarkRecipes: ", cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getRecipes(String day, String month, int year)
    {
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT image, name, cooktime, calories\n" +
                    "FROM Schedule, Recipes\n" +
                    "WHERE userID LIKE \"sample_user\"\n AND date LIKE \"" + year + "-" + month + "-" + day + "\"\n" +
                    "AND Recipes.id=recipeID\n" +
                    "ORDER BY meal;", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
                list.add(cursor.getString(2));
                list.add(cursor.getString(3));
                cursor.moveToNext();
            }
            cursor.close();
        }
        finally{
            list.add("Today is an empty day");
        }
        return list;
    }
    public List<String> getRecipeFromSearch(String name)
    {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id, name, calories, carbs , sodium, fat, protein, fiber, cooktime, servings, instructions, image FROM Recipes " +
                "WHERE name like '"+ name +"' ;", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            list.add(cursor.getString(1));
            list.add(cursor.getString(2));
            list.add(cursor.getString(3));
            list.add(cursor.getString(4));
            list.add(cursor.getString(5));
            list.add(cursor.getString(6));
            list.add(cursor.getString(7));
            list.add(cursor.getString(8));
            list.add(cursor.getString(9));
            list.add(cursor.getString(10));
            Log.d("zzzzzz",list.get(10));
            list.add(cursor.getString(11));


            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getIngredients(String recipeId)
    {
        /*
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Ingredients.name, Ingredients.amount, Ingredients.mUnit "+
                " FROM Ingredients "+
                " WHERE Ingredients.id= '"+recipeId+ "' or Ingredients.amount=NULL  or Ingredients.mUnit=NULL;", null);

        cursor.moveToFirst();
<<<<<<< HEAD
        int count=0;
        int c=0;
        Log.d("loster","listsize........."+cursor.getString(count+1));
=======



>>>>>>> master
        while (!cursor.isAfterLast()) {
            Log.d("rerererer","listsize: "+list.size()+" count: "+count);
            list.add(cursor.getString(0)+" "+cursor.getString(1)+""+cursor.getString(2));

<<<<<<< HEAD
=======
            list.add(cursor.getString(0));

>>>>>>> master
            cursor.moveToNext();

        }
        cursor.close();
<<<<<<< HEAD



        return list;
        */
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Ingredients.name "+
                " FROM Ingredients "+
                " WHERE Ingredients.id= '"+recipeId+ "' or Ingredients.amount=NULL  or Ingredients.mUnit=NULL;", null);

        cursor.moveToFirst();
        int count=0;
        int c=0;
        //Log.d("loster","listsize........."+cursor.getString());
        while (!cursor.isAfterLast()) {
            Log.d("rerererer","listsize: "+list.size()+" count: "+count);
            list.add(cursor.getString(0));

            cursor.moveToNext();

        }
        cursor.close();

=======
        //System.out.println(list.size());
>>>>>>> master


        return list;



    }

    //----------------------------------------------------------------------------------------------

    public List<String> getRecipesFromHome(String date)
    {
        List<String> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT image, name, cooktime, calories\n" +
                    "FROM Schedule, Recipes\n" +
                    "WHERE userID LIKE \"sample_user\"\n AND date LIKE \"" + date + "\"\n" +
                    "AND Recipes.id=recipeID\n" +
                    "ORDER BY meal;", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
                list.add(cursor.getString(2));
                list.add(cursor.getString(3));
                cursor.moveToNext();
            }
            cursor.close();
        }
        finally{
            list.add("Today is an empty day");
        }
        return list;
    }
    //----------------------------------------------------------------------------------------------
    public String getTargetCal()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT calTarget FROM Profiles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getConsumedCal()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT calCount FROM DailyTracker", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val=cursor.getString(0);
            cursor.moveToNext();
        }

        cursor.close();
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getTargetCarb()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT carbTarget FROM Profiles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getConsumedCarb()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT carbCount FROM DailyTracker", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getTargetPro()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT proteinTarget FROM Profiles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getConsumedPro()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT proteinCount FROM DailyTracker", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val=cursor.getString(0);
            cursor.moveToNext();
        }

        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getTargetFib()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT fiberTarget FROM Profiles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getConsumedFib()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT fiberCount FROM DailyTracker", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getTargetFat()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT fatTarget FROM Profiles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getConsumedFat()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT fatCount FROM DailyTracker", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val=cursor.getString(0);
            cursor.moveToNext();
        }

        cursor.close();
        Val = Val + "g";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getTargetSod()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT sodiumTarget FROM Profiles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "mg";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public String getConsumedSod()
    {
        String Val = "0";
        Cursor cursor = database.rawQuery("SELECT sodiumCount FROM DailyTracker", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Val = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        Val = Val + "mg";
        return Val;
    }
    //----------------------------------------------------------------------------------------------
    public List<String> getUserName()
    {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id FROM Profiles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    //----------------------------------------------------------------------------------------------
    public List<String>  getRandomMealImage()
    {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select image,name from Recipes where id <> 0 ORDER BY RANDOM() LIMIT 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            list.add(cursor.getString(1));

            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    //----------------------------------------------------------------------------------------------


    void UpdateIntake(String cal, String carb, String sod, String fat, String pro, String fib){

        database.execSQL("UPDATE Profiles\n" +
                "SET calTarget="+cal+",\n" +
                "carbTarget = "+carb+",\n" +
                "sodiumTarget = "+sod+",\n" +
                "fatTarget = "+fat+",\n" +
                "proteinTarget = "+pro+",\n" +
                "fiberTarget = "+fib+"\n" +
                "WHERE id LIKE \"sample_user\";");


    }


    void PlannerSetup(){

        database.execSQL("UPDATE Profiles\n" +
                "SET calTarget=2,\n" +
                "carbTarget = 2,\n" +
                "sodiumTarget = 1900,\n" +
                "fatTarget = 67,\n" +
                "proteinTarget = 55,\n" +
                "fiberTarget = 32\n" +
                "WHERE id LIKE \"sample_user\";");


    }

    void droptemp(){
        database.execSQL("DROP TABLE temp;");
    }


    void createTemp(){

        database.execSQL("CREATE TABLE temp (r1 INTEGER, r2 INTEGER, r3 INTEGER, r4 INTEGER, r5 INTEGER, r6 INTEGER);");

    }




    void setuptemptable(){


        database.execSQL("INSERT INTO temp\n" +
                "SELECT r1id, r2id, r3id, r4id, r5id, r6id\n" +
                "FROM (SELECT R1.id as r1id, R2.id as r2id, R3.id as r3id, R4.id as r4id, R5.id as r5id, R6.id as r6id, \n" +
                "(R1.calories + R2.calories) + 2*(R3.calories + R4.calories + R5.calories + R6.calories) as calTotal,\n" +
                "(R1.carbs + R2.carbs) + 2*(R3.carbs + R4.carbs + R5.carbs + R6.carbs) as carbTotal,\n" +
                "(R1.sodium + R2.sodium) + 2*(R3.sodium + R4.sodium + R5.sodium + R6.sodium) as sodTotal,\n" +
                "(R1.fat + R2.fat) + 2*(R3.fat + R4.fat + R5.fat + R6.fat) as fatTotal,\n" +
                "(R1.protein + R2.protein) + 2*(R3.protein + R4.protein + R5.protein + R6.protein) as protTotal,\n" +
                "(R1.fiber + R2.fiber) + 2*(R3.fiber + R4.fiber + R5.fiber + R6.fiber) as fibTotal\n" +
                "FROM Recipes as R1, Recipes as R2, Recipes as R3, Recipes as R4, Recipes as R5, Recipes as R6, Profiles\n" +
                "WHERE R1.similarTo like '%breakfast%' AND R2.similarTo like '%breakfast%' AND\n" +
                "R3.similarTo like '%lunch%' AND R4.similarTo like '%lunch%' AND\n" +
                "R5.similarTo like '%dinner%' AND R6.similarTo like '%dinner%'\n" +
                "AND Profiles.id like \"sample_user\"    -- just say which user profile you're interested in and it'll auto-collect data\n" +
                "AND calTotal>(Profiles.calTarget-100)-300 AND calTotal<(Profiles.calTarget+100)-300    -- here 300 is the snack allowance\n" +
                "AND carbTotal>(Profiles.carbTarget*0.8) AND carbTotal<(Profiles.carbTarget*1.2)\n" +
                "AND sodTotal>(Profiles.sodiumTarget*0.8) AND sodTotal<(Profiles.sodiumTarget*1.2)\n" +
                "AND protTotal>(Profiles.proteinTarget*0.9) AND protTotal<(Profiles.proteinTarget*1.5) -- this is because the prot we have is actually minimum recommended\n" +
                "AND fatTotal>(Profiles.fatTarget*0.8) AND fatTotal<(Profiles.fatTarget*1.15)\n" +
                "AND fibTotal>(Profiles.fiberTarget*0.8) AND fibTotal<(Profiles.fiberTarget*1.2)\n" +
                "-- AND additional conditions like vegetarian-only\n" +
                "AND r1id!=r2id AND r1id!=r3id AND r1id!=r4id AND r1id!=r5id AND r1id!=r6id\n" +
                "AND r2id!=r3id AND r2id!=r4id AND r2id!=r5id AND r2id!=r6id\n" +
                "AND r3id!=r4id AND r3id!=r5id AND r3id!=r6id\n" +
                "AND r4id!=r5id AND r4id!=r6id\n" +
                "AND r5id!=r6id) FitsConditions;");


    }

};



