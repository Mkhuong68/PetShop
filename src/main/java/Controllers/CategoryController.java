package Controllers;

import DAOs.CategoryDAO;
import Model.Category;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {
    
    private CategoryDAO categoryDAO = new CategoryDAO();

    @GET
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @POST
    public Response createCategory(Category category) {
        boolean created = categoryDAO.createCategory(category);
        if (created) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{id}")
    public Response updateCategory(@PathParam("id") int id, Category category) {
        boolean updated = categoryDAO.updateCategory(id, category);
        if (updated) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCategory(@PathParam("id") int id) {
        boolean deleted = categoryDAO.deleteCategory(id);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
