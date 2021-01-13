import nextConnect from "next-connect";
import CourseService from "../../../shared/services/course-service";
import EventSource from "eventsource";

const stream = async (req, res) => {
	console.log("Conenct to SSE stream");

	let eventSource = new EventSource(`${process.env.API_HOST}/courses/sse`);

	eventSource.onopen = (e) => {
		console.log("listen to see endpoint now", e);
	};

	eventSource.onmessage = (e) => {
		res.flushData(e.data);
	};

	eventSource.onerror = (e) => {
		console.log("error ", e);
	};

	res.on("close", () => {
		console.log("close connection...");
		eventSource.close();
		eventSource = null;
		res.end();
	});
};

const sseMiddleware = (req, res, next) => {
	res.setHeader("Content-Type", "text/event-stream");
	res.setHeader("Cache-Control", "no-cache");
	res.flushHeaders();

	const flushData = (data) => {
		const sseFormattedResponse = `data: ${data}\n\n`;
		res.write("event: message\n");
		res.write(sseFormattedResponse);
		res.flush();
	};

	Object.assign(res, { flushData });

	next();
};

const createCourseHandler = async (req, res) => {
	const course = req.body.course;
	const response = await CourseService.create(course);
	if (response.status === 201) {
		res.json({ message: "ok" });
	} else {
		res.statusCode(response.status).json({ status: response.status, message: response.statusText });
	}
};

const handler = nextConnect();
handler.get(sseMiddleware, stream);
handler.post(createCourseHandler);

export default handler;
