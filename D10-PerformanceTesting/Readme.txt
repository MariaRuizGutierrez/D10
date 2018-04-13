Con este fichero queremos aclarar las decisiones que hemos tomado sobre el proyecto Acme Newspaper.


1. Las palabras tabú por defecto en el sistema no se podrán editar, por lo tanto tampoco eliminar.

2. El creador de un periódico es el que podrá crear artículos en él. Un usuario el cual no haya creado ese periódico no podrá crear artículos sobre él. Esta decisión ha sido tomada ya que por ejemplo, si poníamos que cualquier actor pudiera crear artículos en un periódico el cual no había creado él, podía crear artículos en modo borrador y por lo tanto no se llegaría a publicar nunca. Ya que es el creador del artículo el que decide si su artículo está en modo final o no.

3. Para los no autenticados sólo se podrán ver los periódicos públicos y que han sido publicados, los privados no. Ya que un customer paga por ellos. Cuando mostramos los artículos ya sea desde el perfil de usuarios o dandole a los artículos de un periódico solo se mostrarán aquellos que están en modo final, nunca en modo borrador.

4. La fecha publicationDate de un newspaper se pondrá cuando el usuario le de al botón "publish". 

5. La fecha publicationMoment de un artículo se pondrá cuando el usuario ponga el artículo en modo final.

6. Hemos considerado que una suscripción no se pueda editar ni eliminar una vez creada en el sistema.

7. Para el atributo sumará de un artículo hemos decidido un máximo de 500 caracteres y un mínimo de 9.

8. Cuando te logueas como el admin del sistema, siempre vas a mostrar entero los periódicos privados, ya que si los va a borrar necesitan verlo. También mostrarán los artículos de los usuarios siempre que no estén en modo borrador. Todos los artículos que estén en modo borrador no se les mostrará debido a que hemos considerado que como se pueden editar aun el user no lo ha dado por finalizado y puede cambiar el contenido de dicho artículo.

9. Solo habrá un admin en el sistema.

10. Los buscadores depende por cada actor.

ADMIN: Buscador de artículo: Aquellos artículos que están en modo final(ósea que tienen fecha de publicación) y cuyo periódico esté o no en modo público o privado y puede estar o no publicado. Buscador de periódico: Te busca por todos.


USER: Buscador de artículo: Te salen todos tus artículos tanto si están en modo borrador como si no. De los demás usuarios solo salen lo artículos que estén en un periódico público y también publicado. Buscador de periódico: los privados y los públicos de otros users y tuyos que estén publicados. Tus periódicos que no están publicados los podrás ver desde la lista de tus periódicos.

CUSTOMER: Buscador de artículo: Te salen todos aquellos artículos en modo final y cuyo periódico está publicado. Los artículos privados de aquellos periódicos a los cuales estás suscrito. Buscador de periódico: Aquellos que están publicados tanto en modo público como privado. Y los privados a los que tu te has suscrito.

11. En los tests de rendimiento si el tiempo en superior a 2 segundos lo consideramos inválido.

Todas estas decisiones se le comentaron a nuestro profesor de seguimiento, que en este caso ha sido Carlos Muller, y nos ha dado el visto bueno.

