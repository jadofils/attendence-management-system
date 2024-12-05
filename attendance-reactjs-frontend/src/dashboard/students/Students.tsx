/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Sidebar from "../Sidebar";
import DashboardNavbar from "../DashboardNavBar";
import { fetchUsers } from "../dashboardServices/usersService";
import deleteUser from "../../service/users/userService";
import { FiEdit, FiEye, FiTrash2 } from "react-icons/fi";
import UserDetailsModal from "../users/user-details";

interface User {
  userId: number;
  username: string;
  role: string;
  studentProfile: string;
}

const Students: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [filteredUsers, setFilteredUsers] = useState<User[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

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
    const confirmed = window.confirm(
      "Are you sure you want to delete this user?"
    );
    if (confirmed) {
      try {
        await deleteUser(userId.toString());
        setUsers(users.filter((user) => user.userId !== userId));
        setFilteredUsers(
          filteredUsers.filter((user) => user.userId !== userId)
        );
        alert("User deleted successfully");
      } catch (error) {
        console.error("Failed to delete user:", error);
        alert("Failed to delete user");
      }
    }
  };

  const handleViewUser = (user: User) => {
    setSelectedUser(user);
  };
  const closeUserModal = () => {
    setSelectedUser(null);
  };



  

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div>
      {/* User Details Modal */}
      {selectedUser && (
        <UserDetailsModal user={selectedUser} onClose={closeUserModal} />
      )}

      <Sidebar />
      <DashboardNavbar />

      <div className="container mx-lg px-4 sm:px-8 mt-48 ml-64">
        {/* Cards Section */}
        <div className="max-w-[95%] mx-auto mr-52">
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 my-4">
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold text-blue-600">Total Users</h2>
              <p className="text-2xl">{users.length}</p>
            </div>
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold text-blue-600">Active Users</h2>
              <p className="text-2xl">
                {users.filter((user) => user.role === "active").length}
              </p>
            </div>
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold text-blue-600">Admins</h2>
              <p className="text-2xl">
                {users.filter((user) => user.role === "admin").length}
              </p>
            </div>
            <div className="bg-primary shadow-md p-4 rounded">
              <h2 className="text-lg font-semibold text-blue-600">Other roles</h2>
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
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold text-blue-600 bg-primary z-10">
                    ID
                  </th>
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold text-blue-600 bg-primary z-10">
                    Username
                  </th>
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold text-blue-600 bg-primary z-10">
                    Role
                  </th>
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold text-blue-600 bg-primary z-10">
                    Student Profile Image
                  </th>
                  <th className="sticky top-0 px-5 py-3 border-b-2 border-gray-200 text-sm font-semibold text-blue-600 bg-primary z-10">
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
                        src={`http://localhost:8080/uploads/${user.studentProfile}`}
                        alt={`${user.username}'s student profile`}
                        className="w-10 h-10 rounded-full border border-gray-300 shadow-md"
                      />
                    </td>
                    <td className="px-5 py-3 border-b border-gray-200 text-sm flex items-center">
                      <FiEye
                        className="text-blue-500 hover:text-blue-700 cursor-pointer mr-4"
                        onClick={() => handleViewUser(user)}
                      />
                      <Link
                        to={`/signup?userId=${user.userId}&username=${user.username}`}
                        className="text-green-500 hover:text-green-700 cursor-pointer mr-4"
                      >
                        <FiEdit />
                      </Link>
                      <FiTrash2
                        className="text-red-500 hover:text-red-700 cursor-pointer"
                        onClick={() => handleDelete(user.userId)}
                      />
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

export default Students;
