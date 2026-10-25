export default async function logMiddleware({ to, from, next }) {
  console.log("log middleware");
  return next();
}
