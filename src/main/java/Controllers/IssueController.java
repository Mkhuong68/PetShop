package Controllers;

import DAOs.IssueDAO;
import Model.Issue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("issues")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IssueController {

    private IssueDAO issueDAO = new IssueDAO();

    // (61) Lấy danh sách tất cả các vấn đề đã báo cáo
    @GET
    public List<Issue> getAllIssues() {
        return issueDAO.getAllIssues();
    }

    // (62) Cập nhật trạng thái vấn đề
    @PUT
    @Path("{id}")
    public Response updateIssueStatus(@PathParam("id") int id, @QueryParam("status") String status) {
        boolean updated = issueDAO.updateIssueStatus(id, status);
        if (updated) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
