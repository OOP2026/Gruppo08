package implementazioneDao.entity;

/***
 * Analogamente a model.Identifiable, IdentifiableEntity e' un interfaccia che
 * generalizza il concetto di chiave primaria di un DB
 * @param <I> tipo dell'attributo chiave primaria
 */
public interface IdentifiableEntity<I> {
	I getId();
}
