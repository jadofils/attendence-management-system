 
import React from 'react';

interface User {
  userId: number;
  username: string;
  role: string;
  studentProfile: string;
}

interface UserDetailsModalProps {
  user: User | null;
  onClose: () => void;
}

const UserDetailsModal: React.FC<UserDetailsModalProps> = ({ user, onClose }) => {
  if (!user) return null;
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white p-8 rounded-lg shadow-xl w-96 relative">
        <button 
          onClick={onClose} 
          className="absolute top-4 right-4 text-gray-600 hover:text-gray-900"
         >
          ✕
        </button>
        <div className="flex flex-col items-center">
          <div className="relative">
            <img
              src={`http://localhost:8080/uploads/${user.studentProfile}`}
              alt={`${user.username}'s profile`}
              className="w-32 h-32 rounded-full border-4 border-primary mb-4 object-cover"
            />
            <div className="absolute inset-0 bg-black opacity-50 rounded-full"></div>
            <div className="absolute inset-0 flex items-center justify-center">
              <h2 className="text-white text-lg font-semibold">{user.username}</h2>
            </div>
          </div>
          <h2 className="text-2xl font-bold mb-2">{user.username}</h2>
          <p className="text-gray-600 mb-4">User ID: {user.userId}</p>
          <div className="w-full">
            <div className="flex justify-between mb-2">
              <span className="font-semibold">Role:</span>
              <span>{user.role}</span>
            </div>
            {/* You can add more user details here */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserDetailsModal;
