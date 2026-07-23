package controller.cache;

import java.util.ArrayList;
import java.util.List;

import dao.GenericDao;
import implementazioneDao.entity.IdentifiableEntity;

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
