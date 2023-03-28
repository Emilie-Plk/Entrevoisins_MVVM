package com.example.entrevoisins_mvvm.data.repository;

import androidx.lifecycle.LiveData;

import com.example.entrevoisins_mvvm.data.dao.NeighbourDao;
import com.example.entrevoisins_mvvm.data.entities.NeighbourEntity;

import java.util.List;
import java.util.concurrent.Executor;

public class NeighboursRepository {

    private final NeighbourDao dao;

    private final Executor executor;

    public NeighboursRepository(NeighbourDao dao, Executor executor) {
   /*     if (buildConfigResolver.isDebug()) {
            generateNeighbours();
        }*/
        this.dao = dao;
        this.executor = executor;
    }

    public void addNeighbour(NeighbourEntity neighbour) {
        executor.execute(() ->
                dao.insertNeighbour(neighbour));
    }

    public void deleteNeighbour(long neighbourID) {
        executor.execute(() ->
                dao.deleteNeighbour(neighbourID));
    }

    public LiveData<List<NeighbourEntity>> getNeighbourEntities() {
        return dao.getNeighbourEntities();
    }

    public void updateFavorite(long neighbourId, boolean isFavorite) {
        executor.execute(() -> dao.updateFavorite(neighbourId, isFavorite));
    }

    public LiveData<NeighbourEntity> getDetailNeighbourInfo(long neighbourId) {
        return dao.getDetailNeighbourInfo(neighbourId);
    }

    public NeighbourEntity getNeighbourEntity(long neighbourId) {
    return dao.getNeighbour(neighbourId);
    }

   /* public static final List<NeighbourEntity> DUMMY_NEIGHBOURS = Arrays.asList(
            new NeighbourEntity(1, isFavorite, "Caroline", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Saint-Pierre-du-Mont ; 5km",
                    "+33 6 86 57 90 14", "Bonjour ! Je souhaite rencontrer des gens pour jouer à des jeux de cartes et discuter de tout et de rien."

            ),
            new NeighbourEntity(2, isFavorite, "Jack", "https://i.pravatar.cc/150?u=a042581f4e29026704e", "Grenoble : 20km",
                    "+33 6 89 56 26 32", "Bonjour ! Je souhaiterais faire de la marche nordique. Pas initié, je recherche une ou plusieurs personnes susceptibles de m'accompagner ! J'aime les jeux de cartes tels la belote et le tarot.."

            ),
            new NeighbourEntity(3, isFavorite, "Chloé", "https://i.pravatar.cc/150?u=a042581f4e29026704f", "Labastide-d'Armagnac ; 7km",
                    "+33 6 86 57 92 68", "Bonjour ! Je suis à la recherche de personnes pour mon club de lecture. Fanas de SF, n'hésitez plus et rejoignez-nous !"

            ),
            new NeighbourEntity(4, isFavorite, "Vincent", "https://i.pravatar.cc/150?u=a042581f4e29026704a", "Saint-Pierre-du-Mont ; 5km",
                    "+33 6 56 26 26 26", "Bonjour ! J'adore les films d'arts martiaux et je cherche des gens pour m'accompagner !"

            ),
            new NeighbourEntity(5, isFavorite, "Elodie", "https://i.pravatar.cc/150?u=a042581f4e29026704b", "Mugron ; 6km",
                    "+33 6 58 58 58 58", "Bonjour ! Je suis à la recherche de personnes qui souhaiteraient voyager !"

            ),
            new NeighbourEntity(6, isFavorite, "Sylvain", "https://i.pravatar.cc/150?u=a042581f4e29026704c", "Saint-Pierre-du-Port ; 15km",
                    "+33 6 86 57 90 44", "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot.."

            ),
            new NeighbourEntity(7, isFavorite, "Laetitia", "https://i.pravatar.cc/150?u=a042581f4e29026703d", "Saint-Pierre-du-Mont ; 5km",
                    "+33 6 86 68 90 14", "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot.."
            ));
*/
   /* public static List<NeighbourEntity> generateNeighbours() {
        return new ArrayList<>(DUMMY_NEIGHBOURS);
    }*/


}
