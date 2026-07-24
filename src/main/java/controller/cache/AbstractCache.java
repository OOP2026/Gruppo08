package controller.cache;

import java.util.ArrayList;
import java.util.List;

import dao.GenericDao;
import implementazioneDao.entity.IdentifiableEntity;

/***
 * Classe astratta estesa da tutte le classi del package controller.cache.
 * <br>
 * Contiene una lista che funge da database in memory, ogni operazione getById e' implementata
 * in modo da recuperare il dato dal db e importarlo nel db in memory.
 * @param <E> classe entita', di implementazioneDao.entity
 * @param <I> tipo della chiave primaria di E
 * @param <D> dao utilizzato per recuperare i dati dal db
 */
public abstract class AbstractCache<E extends IdentifiableEntity<I>, I, D extends GenericDao<E, I>> {
	protected AbstractCache(D sqldao) {
		this.sqldao = sqldao;
	}

	protected D sqldao;

	protected List<E> inMem = new ArrayList<>();

	public E getById(I id) {
		E entity = null;

		for (E e : inMem) {
			if (e.getId().equals(id))
				return e;
		}

		entity = sqldao.getById(id);
		inMem.add(entity);

		return entity;
	}

}
