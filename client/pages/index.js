import React, { useState, useEffect } from "react";
import Head from "next/head";
import Link from "next/link";
import CourseService from "../shared/services/course-service";

export default function Home({ initialCourses = [] }) {
	const [courses, setCourses] = useState(initialCourses);

	useEffect(() => {
		let eventSource = new EventSource("/api/course");

		eventSource.onopen = (e) => {
			console.log("listen to api-sse endpoint", e);
		};

		eventSource.onmessage = (e) => {
			const course = JSON.parse(e.data);

			if (!courses.includes(course)) {
				setCourses((courses) => [...courses, course]);
			}
		};

		eventSource.onerror = (e) => {
			console.log("error", e);
		};

		return () => {
			eventSource.close();
			eventSource = null;
		};
	}, []);

	const courseList = courses.map((course) => (
		<tr key={course.id}>
			<td style={{ whiteSpace: "nowrap" }}>{course.title}</td>
			<td>{course.duration}</td>
		</tr>
	));

	return (
		<div>
			<Head>
				<title>Course Service Example</title>
				<link rel="icon" href="/favicon.ico" />
			</Head>

			<div>
				<h1>Courses</h1>
				<Link href="/course">
					<button>
						<a>New Course</a>
					</button>
				</Link>
				<table>
					<thead>
						<th style={{ width: "60%" }}>Title</th>
						<th style={{ width: "40%" }}>Duration (minutes)</th>
					</thead>
					<tbody>{courseList}</tbody>
				</table>
			</div>
		</div>
	);
}

export const getServerSideProps = async () => {
	const res = await CourseService.findAll();
	const courses = await res.json();
	return {
		props: { initialCourses: courses },
	};
};
