/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom"; // Import Link
import Sidebar from "./Sidebar"; // Adjust import path
import DashboardNavbar from "./DashboardNavBar";
import { fetchUsers } from "./dashboardServices/usersService"; // Import fetchUsers from userService
import deleteUser from "../service/userService";

interface User {
  userId: number;
  username: string;
  role: string;
  studentProfile: string;
 // Date: string;
}

const Users: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [filteredUsers, setFilteredUsers] = useState<User[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState<string>("");

  useEffect(() => {
    const loadUsers = async () => {
      try {
        setIsLoading(true);
        const fetchedUsers = await fetchUsers();
        setUsers(fetchedUsers);
        setFilteredUsers(fetchedUsers);
      } catch (err: any) {
        setError(`Failed to fetch users: ${err.message}`);
      } finally {
        setIsLoading(false);
      }
    };

    loadUsers();
  }, []);

  useEffect(() => {
    const filtered = users.filter((user) =>
      user.username.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredUsers(filtered);
  }, [searchTerm, users]);
  
  const handleDelete = async (userId: number) => {
    const confirmed = window.confirm("Are you sure you want to delete this user?");
    if (confirmed) {
      try {
        await deleteUser(userId.toString()); // Call deleteUser service
        setUsers(users.filter((user) => user.userId !== userId)); // Remove deleted user from state
        setFilteredUsers(filteredUsers.filter((user) => user.userId !== userId)); // Update filtered users
        alert("User deleted successfully");
      } catch (error) {
        console.error("Failed to delete user:", error);
        alert("Failed to delete user");
      }
    }
  };



  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div>
      <Sidebar />
      <DashboardNavbar />

      <div className="container mx-lg px-4 sm:px-8 mt-48 ml-64">
        {/* Cards Section */}
        <div className="max-w-[95%] mx-auto mr-52">
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 my-4">
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold">Total Users</h2>
              <p className="text-2xl">{users.length}</p>
            </div>
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold">Active Users</h2>
              <p className="text-2xl">
                {users.filter((user) => user.role === "active").length}
              </p>
            </div>
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold">Admins</h2>
              <p className="text-2xl">
                {users.filter((user) => user.role === "admin").length}
              </p>
            </div>
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold">Other roles</h2>
              <p className="text-2xl">
                {
                  users.filter(
                    (user) => user.role !== "admin" && user.role !== "active"
                  ).length
                }
              </p>
            </div>
          </div>
        </div>

        {/* Search Bar */}
        <div className="my-4 w-[35%]">
          <input
            autoComplete="off"
            type="text"
            placeholder="Search users..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>

        {/* Table */}
        <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
          <div
            className="inline-block min-w-full shadow rounded-lg overflow-hidden"
            style={{ maxHeight: "400px", overflowY: "auto" }}
          >
            <table className="min-w-[95%] leading-normal bg-secondary">
              <thead>
                <tr className="bg-primary text-left">
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold bg-primary z-10">
                    ID
                  </th>
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold bg-primary z-10">
                    Username
                  </th>
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold bg-primary z-10">
                    role
                  </th>
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold bg-primary z-10">
                    studentProfile Image
                  </th>
                  {/* <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold bg-primary z-10">
                    Created At
                  </th> */}
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold bg-primary z-10">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody>
                {filteredUsers.slice(0, 10).map((user) => (
                  <tr key={user.userId} className="hover:bg-gray-100">
                    <td className="px-5 py-3 border-b border-gray-200 text-sm">
                      {user.userId}
                    </td>
                    <td className="px-5 py-3 border-b border-gray-200 text-sm">
                      {user.username}
                    </td>
                    <td className="px-5 py-3 border-b border-gray-200 text-sm">
                      {user.role}
                    </td>
                    <td className="px-5 py-3 border-b border-gray-200 text-sm">
                      <img
                        src={`http://localhost:8080/uploads/${user.studentProfile}`} // Adjust the base URL according to your backend
                        alt={`${user.username}'s student profile`}
                        className="w-10 h-10 rounded-full border border-gray-300 shadow-md"
                      />
                    </td>
                    {/* <td className="px-5 py-3 border-b border-gray-200 text-sm">
                      {user.Date}
                    </td> */}
                    <td className="px-5 py-3 border-b border-gray-200 text-sm">
                      <button
                        className="bg-button text-white py-1 px-3 rounded mr-2"
                        onClick={() =>
                          alert(
                            `View user: ${user.username} and ${user.userId}`
                          )
                        }
                      >
                        View
                      </button>
                      <Link
                        to={`/signup?userId=${user.userId}&username=${user.username}`}
                        className="bg-green-500 hover:bg-green-600 text-white py-1 px-3 rounded shadow-md transition duration-300"
                      >
                        Update
                      </Link>
                      <button
                        className="bg-red-500 text-white py-1 px-3 rounded ml-2"
                        onClick={() => handleDelete(user.userId)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {filteredUsers.length === 0 && (
              <div className="text-center py-4 text-gray-500">
                No users found
              </div>
            )}
          </div>
          {/* Redirect Button */}
          <div className="text-right mb-4 mr-52 mt-4">
            <Link
              to="/signup"
              className="bg-button text-white py-2 px-4 rounded shadow-md hover:bg-button-hover transition duration-300"
            >
              Add New User
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Users;
