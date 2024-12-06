import axios from "axios";

export interface Course {
    courseId: number;
    courseName: string;
    courseDescription: string;
    credits: number;
}

export interface Program {
    programId: number;
    programName: string;
    programDescription: string;
    courses: Course[];
}

export interface Student {
    studentId: number;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    enrollmentDate: string;
    program: Program;
}

// Base URL of the backend
const BASE_URL = 'http://localhost:8080/api/students';

// Fetch all students
export const fetchAllStudents = async (): Promise<Student[]> => {
    try {
        const response = await axios.get<Student[]>(BASE_URL);
        console.log(response.data)
        return response.data;
    } catch (error) {
        console.error('Error fetching students:', error);
        throw error;
    }
};
