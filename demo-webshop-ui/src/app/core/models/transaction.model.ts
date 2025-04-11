import { Product } from "./product.model";
import { User } from "./user.model";

export interface Transaction {
	id: number;
	description: string;
	product: Product;
	user: User | undefined;
	createdAt: Date;
}
