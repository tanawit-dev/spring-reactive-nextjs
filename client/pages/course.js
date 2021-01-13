import React, { useState } from "react";
import Link from "next/link";
import CategoryService from "../shared/services/category-service";

const NewCourse = ({ categories = [] }) => {
	const emptyCourse = {
		title: null,
		description: null,
		categoryId: categories[0].id,
		duration: 60,
	};

	const [course, setCourse] = useState(emptyCourse);
	const [saved, setSaved] = useState(false);
	const [error, setError] = useState(null);

	const handleSubmit = async (e) => {
		e.preventDefault();

		const res = await fetch("/api/course", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ course: course }),
		});
		if (res.ok) {
			setSaved(true);
		} else {
			setError(`Error ${res.status} :: ${res.statusText}`);
		}
	};

	const handleChange = (e) => {
		e.preventDefault();

		const { name, value } = e.target;
		setCourse((prevState) => ({
			...prevState,
			[name]: value,
		}));
	};

	const categoryOptions = categories.map((c) => (
		<option key={c.id} value={c.id}>
			{c.title}
		</option>
	));

	return (
		<div>
			<h1>Create New Course</h1>
			<Link href="/">
				<button>
					<a>All Course</a>
				</button>
			</Link>

			{error && <h3>{error}</h3>}
			{saved && <h3>Congrats! The course '{course.title}' was saved successfully.</h3>}

			{!saved && (
				<form onSubmit={handleSubmit}>
					<label htmlFor="title">Course Title</label>
					<input type="text" id="title" name="title" placeholder="Course Title" value={course.title || ""} onChange={handleChange} />

					<label htmlFor="duration">Course Duration</label>
					<input type="text" id="duration" name="duration" placeholder="Course Duration" value={course.duration || ""} onChange={handleChange} />

					<label htmlFor="category">Course Category</label>
					<select id="category" name="categortId" value={course.categortId || ""} onChange={handleChange}>
						{categoryOptions}
					</select>

					<label htmlFor="description">Description</label>
					<textarea rows="10" id="description" name="description" placeholder="Description" value={course.description} onChange={handleChange} />

					<input type="submit" value="Submit" />
				</form>
			)}
		</div>
	);
};

export const getServerSideProps = async () => {
	const res = await CategoryService.findAll();
	const categories = await res.json();

	return { props: { categories } };
};

export default NewCourse;
