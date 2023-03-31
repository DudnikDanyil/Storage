import path from 'path'
import { fileURLToPath } from 'url';
import HtmlWebpackPlugin  from 'html-webpack-plugin'
import MiniCssExtractPlugin  from 'mini-css-extract-plugin'
import {WebpackManifestPlugin}  from 'webpack-manifest-plugin'
import webpack from 'webpack';
const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)
let MODE = process.env.NODE_ENV === 'production' ? 'production' : 'development';

let config = {
    mode:MODE,
    entry: {
      main: './src/index.jsx',
    },
    output: {
      path: path.resolve(__dirname, './dist'),
      filename: 'static/js/[name].js',
      clean:true
    }, 
    devtool:'eval',
    // stats: 'none',
    devServer: {
     open: true,
     hot:true,
     client: {
      logging: 'error',
      overlay: false,
    },
    },
    module: {
      rules: [
        {
          test: /\.(png|jpeg|jpg|svg|webp)$/i,
          loader: 'file-loader',
          options: {
            name: '[name].[hash].[ext]',
            outputPath: 'static/media',
          },
        },
        {
          test: /\.(tsx|ts|js|jsx)$/,
          exclude:'/node_modules/',
          loader: 'babel-loader',
        },
        {
        test: /\.s[ac]ss$/i,
        use: [
          {
            loader: "style-loader",
            options: {
              esModule: false,
            },
          },
          {
            loader:MiniCssExtractPlugin.loader,
            options: {
              esModule: false,
            }
          },
          {
            loader: "css-loader",
            options: {
              esModule: false,
            }
          },
          {
            loader: "sass-loader",
          },
        ]}
      ]  
      },
      resolve: {
        // extensions: ['.js', '.jsx', '.ts', '.tsx'],
        extensions: ['.js', '.jsx'],
      },
    plugins: [new HtmlWebpackPlugin({
      minify: MODE === "production",
      template:path.resolve(__dirname, './public/index.html'),
      filename:'index.html'
    }),
    new MiniCssExtractPlugin({
      filename:'static/css/[name].css',
    }),
    new WebpackManifestPlugin({
      publicPath: '/'
    }),
    new webpack.EnvironmentPlugin({
      PUBLIC_URL: './public', 
    })
  ],
  };
  export default config