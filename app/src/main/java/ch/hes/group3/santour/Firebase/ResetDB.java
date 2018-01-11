package ch.hes.group3.santour.Firebase;

import android.util.Log;

import ch.hes.group3.santour.DTO.Category;
import ch.hes.group3.santour.DTO.POD;
import ch.hes.group3.santour.DTO.PODCategory;
import ch.hes.group3.santour.DTO.POI;
import ch.hes.group3.santour.DTO.Position;
import ch.hes.group3.santour.DTO.Role;
import ch.hes.group3.santour.DTO.Track;
import ch.hes.group3.santour.DTO.Type;
import ch.hes.group3.santour.DTO.User;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aleks on 21.11.2017.
 */

public class ResetDB {

    private final static DatabaseReference DB_REFERENCE = FirebaseDatabase.getInstance().getReference();

    /**
     * this method bas used to reset all the data from the db
     */
    public static void resetDB() {
        //remove everything from DB
        DB_REFERENCE.removeValue();

        //add a new role
        RoleDB.createRole(new Role("admin"), new DataListener() {
            @Override
            public void onSuccess(Object object) {
                Role role = (Role) object;
                //now that the role has been created, create the user
                UserDB.createUser(new User("root", "root", "root@root.ch", role.getId()), new DataListener() {
                    @Override
                    public void onSuccess(Object object) {
                        final User user = (User) object;
                        //create a category
                        CategoryDB.createCategory(new Category("Slope"));
                        CategoryDB.createCategory(new Category("Rocks"));
                        CategoryDB.createCategory(new Category("Verticality"), new DataListener() {
                            @Override
                            public void onSuccess(Object object) {
                                final Category category = (Category) object;
                                //create a type
                                TypeDB.createType(new Type("Walk"), new DataListener() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        final Type type = (Type) object;
                                        //now create a new track
                                        //create one poisition
                                        final Position position = new Position(1.450, 1.560, 1285.47, new Date().toString());
                                        //create a list of position for the track
                                        final List<Position> positions = new ArrayList<>();
                                        positions.add(position);
                                        positions.add(position);
                                        positions.add(position);
                                        positions.add(position);
                                        //create a POI
                                        final POI poi = new POI("Cervin", "Picture", "Très beau", position);
                                        //create a list of POI for the track
                                        final List<POI> pois = new ArrayList<>();
                                        pois.add(poi);
                                        pois.add(poi);
                                        pois.add(poi);
                                        pois.add(poi);
                                        //create one PODCategory
                                        final PODCategory podCategory = new PODCategory(category.getId(), 5);
                                        //create a list of PODCategory for the PODS
                                        final List<PODCategory> podCategories = new ArrayList<>();
                                        podCategories.add(podCategory);
                                        podCategories.add(podCategory);
                                        podCategories.add(podCategory);
                                        podCategories.add(podCategory);
                                        //create one POD
                                        final POD pod = new POD("Dangerous POD", "PICTURE POD", "très dangerous", position, podCategories);
                                        //create a list of POD for the tracks
                                        final List<POD> pods = new ArrayList<>();
                                        pods.add(pod);
                                        pods.add(pod);
                                        pods.add(pod);
                                        pods.add(pod);
                                        TrackDB.createTrack(new Track("first track", "description track", 2.4, 500, pois, pods, positions, user.getId(), type.getId()), new DataListener() {
                                            @Override
                                            public void onSuccess(Object object) {
                                            }

                                            @Override
                                            public void onFailed(Object dbError) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onFailed(Object dbError) {

                                    }
                                });
                            }

                            @Override
                            public void onFailed(Object dbError) {

                            }
                        });
                    }

                    @Override
                    public void onFailed(Object dbError) {

                    }
                });
            }

            @Override
            public void onFailed(Object dbError) {

            }

        });
    }
}
