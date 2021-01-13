const API_HOST = process.env.API_HOST;

const CourseService = {
	findAll: () => fetch(`${API_HOST}/courses`),
	create: (course) => fetch(`${API_HOST}/courses`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(course) }),
};

export default CourseService;
