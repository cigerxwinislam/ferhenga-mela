import { withNextVideo } from "next-video/process";
/** @type {import('next').NextConfig} */
const nextConfig = {

    experimental: { nftTracing: true }
};

export default withNextVideo(nextConfig);