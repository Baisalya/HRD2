package com.app.hrdrec.network


import com.app.hrdrec.admin.roles.DataRolesPayload
import com.app.hrdrec.admin.roles.UpdateRolesPayload
import com.app.hrdrec.admin.roles.get_all_roles_response.GetAllRolesResponse
import com.app.hrdrec.admin.users.AddUserPayload
import com.app.hrdrec.admin.users.UpdateUserPayload
import com.app.hrdrec.admin.users.employes_response.GetEmployeeByOrgResponse
import com.app.hrdrec.admin.users.user_models.GetAllUserResponse
import com.app.hrdrec.home.AllModuleResponse
import com.app.hrdrec.leaves.response.AddLeave
import com.app.hrdrec.login.model.UserInfo
import com.app.hrdrec.login.model.LoginParams
import com.app.hrdrec.organization.clients.addclients.AddClientsParams
import com.app.hrdrec.organization.clients.addclients.UpdateClientsParams
import com.app.hrdrec.organization.clients.get_clients_response.GetAllClientsResponse
import com.app.hrdrec.organization.locations.addlocation.AddLocationParams
import com.app.hrdrec.organization.locations.get_location_response.GelAllLocationResponse
import com.app.hrdrec.organization.locations.GetCountryResponse
import com.app.hrdrec.organization.locations.addlocation.UpdateLocationParams
import com.app.hrdrec.organization.locations.state_response.GetStateResponse
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.leaves.response.AllLeaveResponse
import com.app.hrdrec.leaves.response.LeaveBalanceResponse
import com.app.hrdrec.leaves.response.LeaveTypeResponse
import com.app.hrdrec.manager.all_leave_submitted.GetLeaveManagerModel
import com.app.hrdrec.manager.response.all_submitted_response.GetSubmitedResponse
import com.app.hrdrec.timesheet.request_payload.SavedDataResponse
import com.app.hrdrec.timesheet.response.TimeScheduleResponse
import com.app.hrdrec.timesheet.response.project_response.ProjectDataResposne
import com.app.hrdrec.utils.CommonResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {


    @POST("users/login")
    suspend fun loginUser(@Body loginRequest: LoginParams): Response<UserInfo>

    @GET("modules/getAll")
    suspend fun gelAllModule(): Response<AllModuleResponse>

    @GET("locations/getAll/{id}")
    suspend fun getAllLocations(@Path("id") id: Int): Response<GelAllLocationResponse>

    @POST("locations/save")
    suspend fun saveLocation(@Body loginRequest: AddLocationParams): Response<CommonResponse>

    @POST("locations/save")
    suspend fun updateLocation(@Body loginRequest: UpdateLocationParams): Response<CommonResponse>

    @DELETE("locations/deleteById/{id}")
    suspend fun deleteLocation(@Path("id") id: Int): Response<CommonResponse>

    @GET("roles/getAll/{id}")
    suspend fun getAllRoles(@Path("id") id: Int): Response<GetAllRolesResponse>

    @GET("users/getAll/{id}")
    suspend fun getAllUsers(@Path("id") id: Int): Response<GetAllUserResponse>





//    @DELETE("roles/deleteById/{id}")
//    suspend fun deleteRole(@Path("id") id: Int): Response<CommonResponse>
//
//    @DELETE("users/deleteById/{id}")
//    suspend fun deleteUser(@Path("id") id: Int): Response<CommonResponse>


    @GET("countries/getAll")
    suspend fun getCountries(): Response<GetCountryResponse>


    //http://106.51.52.207:8092/hrdome/countries/getById/{id}
    @GET("countries/getById/{id}")
    suspend fun getStates(@Path("id") id: Int): Response<GetStateResponse>


    @POST("roles/save")
    suspend fun addRoles(@Body body: DataRolesPayload): Response<CommonResponse>

    @POST("roles/save")
    suspend fun updateRoles(@Body body: UpdateRolesPayload): Response<CommonResponse>


    @GET("employees/getAll/{id}")
    suspend fun getEmployeeByOrg(@Path("id") id: Int): Response<GetEmployeeByOrgResponse>

    @POST("users/save")
    suspend fun addUser(@Body body: AddUserPayload): Response<CommonResponse>

    @POST("users/save")
    suspend fun updateUser(@Body body: UpdateUserPayload): Response<CommonResponse>

    @DELETE("users/deleteById/{id}")
    suspend fun deleteusers(@Path("id") id: Int): Response<CommonResponse>



    @GET("projectclients/getAll/{id}")
    suspend fun getAllClients(@Path("id") id: Int): Response<GetAllClientsResponse>

    @POST("projectclients/save")
    suspend fun saveClients(@Body loginRequest: AddClientsParams): Response<CommonResponse>

    @POST("projectclients/save")
    suspend fun updateClients(@Body loginRequest: UpdateClientsParams): Response<CommonResponse>

    @DELETE("projectclients/deleteById/{id}")
    suspend fun deleteClients(@Path("id") id: Int): Response<CommonResponse>

    @POST("timesheets/save")
    suspend fun saveTimeSheet(@Body addTimeModal: AddTimeModal): Response<CommonResponse>

    @GET("timesheets/getAll/{id}")
    suspend fun getTimesheetscheduler(@Path("id") id: Int): Response<TimeScheduleResponse>

    @GET("timesheets/getAll/{id}")
    suspend fun getTimesheetschedulerDates(@Path("id") id: Int,@QueryMap params: HashMap<String,String>): Response<TimeScheduleResponse>

//    @POST("timesheets/save")
//    suspend fun addTimesheetscheduler(@Body addTimeModal: AddTimeModal): Response<TimeScheduleResponse>

    @GET("leaves/getAll/{id}")
    suspend fun getAllLeaves(@Path("id") id: Int): Response<AllLeaveResponse>

    @GET("leaves/getAll/{id}")
    suspend fun getAllLeavesDates(@Path("id") id: Int,@QueryMap params: HashMap<String,String>): Response<AllLeaveResponse>

    @GET("timesheets/getById/{id}")
    suspend fun getSavedTimesheetById(@Path("id") id: Int): Response<SavedDataResponse>



    @GET("projects/getAllProjects/{id}")
    suspend fun getprojects(@Path("id") id: Int): Response<ProjectDataResposne>



    @GET("leavetype/getAllByLocationId/{id}")
    suspend fun getLeaveTypes(@Path("id") id: Int): Response<LeaveTypeResponse>

    @GET("leavebalances/getAll/{id}/{year}")
    suspend fun getLeaveBalance(@Path("id") id: Int,@Path("year") year: Int): Response<LeaveBalanceResponse>

    @POST("leaves/save")
    suspend fun applyLeave(@Body addTimeModal: AddLeave): Response<CommonResponse>


    @GET("timesheets/getSubmittedByManager/{id}")
    suspend fun getSubmittedByManager(@Path("id") id: Int): Response<GetSubmitedResponse>

    @GET("timesheets/getSubmittedByManager/{id}")
    suspend fun getSubmittedByManagerByDates(@Path("id") id: Int,@QueryMap params: HashMap<String,String>): Response<GetSubmitedResponse>

   /* @GET("timesheets/getSubmittedByManager/{id}")
    suspend fun getSubmittedByManager(@Path("id") id: Int): Response<GetSubmitedResponse>*/

    @FormUrlEncoded
    @POST("timesheets/approve/{timesheetId}/{projectId}")
    suspend fun approveRecord(@Path("timesheetId") timesheetId: Int, @Path("projectId") projectId: Int, @Field("remarks") remarks: String): Response<CommonResponse>

    @FormUrlEncoded
    @POST("timesheets/reject/{timesheetId}/{projectId}")
    suspend fun rejectRecord(@Path("timesheetId") timesheetId: Int, @Path("projectId") projectId: Int, @Field("remarks") remarks: String): Response<CommonResponse>



    @GET("leaves/getAppliedLeavesByManagerId/{id}")
    suspend fun getAllLeaveManager(@Path("id") id: Int): Response<GetLeaveManagerModel>


    @GET("leaves/getAppliedLeavesByManagerId/{id}")
    suspend fun getAllLeaveManagerDates(@Path("id") id: Int,@QueryMap params: HashMap<String,String>): Response<GetLeaveManagerModel>

    @FormUrlEncoded
    @POST("leaves/approve/{id}")
    suspend fun approveLeave(@Path("id") id: Int, @Field("remarks") remarks: String): Response<CommonResponse>

    @FormUrlEncoded
    @POST("leaves/reject/{id}")
    suspend fun rejectLeave(@Path("id") timesheetId: Int, @Field("remarks") remarks: String): Response<CommonResponse>


//    @GET("leaves/getAll/{id}")
//    suspend fun getAllLeave(@Path("id") id: Int): Response<GetAllLeaveResponse>

//    @POST("leaves/save")
//    suspend fun saveLeave(@Body loginRequest: AddLeavePayload): Response<CommonResponse>
//
//    @POST("leaves/save")
//    suspend fun updateLeave(@Body loginRequest: UpdateLeavePayload): Response<CommonResponse>
//
//    @DELETE("leaves/deleteById/{id}")
//    suspend fun deleteLeave(@Path("id") id: Int): Response<CommonResponse>
//
//    @GET("leavetype/getAll/{id}")
//    suspend fun getLeavetypeByOrg(@Path("id") id: Int): Response<GetLeavetypeByOrgResponse>

}