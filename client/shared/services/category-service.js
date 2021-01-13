const API_HOST = process.env.API_HOST;

const CategoryService = {
	findAll: () => fetch(`${API_HOST}/categories`),
};

export default CategoryService;
