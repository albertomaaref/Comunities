package it.dsgroup.comunities.main.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.activities.DetailActivity;
import it.dsgroup.comunities.main.models.Comunity;

/**
 * Created by utente9.academy on 12/12/2017.
 */

public class PushNotification extends Service {

    private DatabaseReference ref;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ChildEventListener child;
    private Comunity comunity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        comunity = (Comunity) InternalStorage.readObject(getApplicationContext(),"comunity");
        ref = database.getReferenceFromUrl("https://comunities-bc5e8.firebaseio.com/gruppi/");
        child = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String gruppo = (String) dataSnapshot.getKey();
                if (comunity.isInTheGroupList(gruppo)){
                    pushValidation(gruppo);
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(child);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void pushValidation(String chiave){
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("nomeGruppo",chiave);
        i.putExtra("notifica",true);
        sendNotificaton(i,"Nuovo post al gruppo",chiave);
    }

    private void sendNotificaton(Intent intent, String s, String chiave) {
        //Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT); // serve per settare l'activity che deve partire al click della notifica

        Uri dsUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this); // creazione oggetto notifica
        builder.setContentTitle(s);
        builder.setContentText(chiave);
        builder.setAutoCancel(true);
        builder.setSound(dsUri);
        builder.setSmallIcon(R.mipmap.ic_launcher);// se non metto questa immagine potrebbe non funzionare la notifica
        //builder.setLargeIcon(bitmap);
        builder.setShowWhen(true);
        builder.setContentIntent(activity);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // l'oggetto che rende visibile l'oggetto notifica
        manager.notify(0, builder.build());
    }
}
